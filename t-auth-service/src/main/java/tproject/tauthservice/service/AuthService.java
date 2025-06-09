package tproject.tauthservice.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import tproject.tauthservice.kafka.producer.UserRegisteredProducer;
import tproject.tauthservice.dto.event.UserRegisteredEvent;
import tproject.tauthservice.dto.response.JwtResponse;
import tproject.tauthservice.dto.request.LoginRequest;
import tproject.tauthservice.dto.request.SignupRequest;
import tproject.tauthservice.dto.AuthenticationDto;
import tproject.tauthservice.dto.response.SignUpResponse;
import tproject.tauthservice.entity.RoleEntity;
import tproject.tauthservice.entity.UserEntity;
import tproject.tauthservice.entity.UserRoleEntity;
import tproject.tauthservice.enumerates.Role;
import tproject.tauthservice.repository.AuthenticationRepository;
import tproject.tauthservice.repository.RoleRepository;
import tproject.tauthservice.repository.UserRepository;
import tproject.tauthservice.entity.AuthenticationEntity;
import tproject.tauthservice.repository.UserRoleRepository;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.restfulresponse.RestfulResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegisteredProducer userRegisteredProducer;
    private final JwtEncoder jwtEncoder;
    private final EntityManager entityManager;
    private final RedissonClient redissonClient;

    private static final int JWT_EXPIRATION_TIME = 3600000;
    private static final int REFRESH_EXPIRATION_TIME = 9000000;
    private static final String BEARER = "Bearer";
    private final AuthenticationRepository authenticationRepository;
    private final UserRoleRepository userRoleRepository;

    public RestfulResponse<JwtResponse> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthenticationDto userDetails = (AuthenticationDto) authentication.getPrincipal();
        log.info("User with id {} has been authenticated {}", userDetails.getUserId(), userDetails);

        String accessToken = generateAccessToken(userDetails);
        String refreshToken = generateRefreshToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtResponse jwtResponse = JwtResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .tokenType(BEARER)
                .id(userDetails.getUserId())
                .username(userDetails.getUsername())
                .roles(roles)
                .expiresIn(JWT_EXPIRATION_TIME / 1000)
                .build();

        return RestfulResponse.success(jwtResponse, ResponseStatus.SUCCESS);

    }

    @Transactional
    public RestfulResponse<SignUpResponse> registerUser(SignupRequest signupRequest) throws InterruptedException {

        String username = signupRequest.getUsername();
        String lockKey = "register_lock:" + username;
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;
        isLocked = lock.tryLock(5, 20, TimeUnit.SECONDS);
        log.info("Lock status for {}: {}", lockKey, isLocked);
        Thread.sleep(15000);

        if (!isLocked) {
            return RestfulResponse.error("username.registration.busy", ResponseStatus.ERROR);
        }
        if (authenticationRepository.existsByUsername(signupRequest.getUsername())
                || userRepository.existsByUsername(signupRequest.getUsername())) {
            //todo: bloom filter
            log.info("Username already exists: {}", signupRequest.getUsername());
            return RestfulResponse.error("username.already.exists", ResponseStatus.ERROR);
        }

        AuthenticationEntity authenticationEntity = new AuthenticationEntity();

        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(signupRequest.getFirstName());
        userEntity.setLastName(signupRequest.getLastName());
        userEntity.setUsername(signupRequest.getUsername());
        userEntity.setBirthDate(signupRequest.getBirthDate());
        Long userId = userRepository.save(userEntity).getId();

        authenticationEntity.setUserId(userId);
        authenticationEntity.setUsername(signupRequest.getUsername());
        authenticationEntity.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        authenticationRepository.save(authenticationEntity).getId();

        RoleEntity role = roleRepository.findByName(Role.USER);
        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .userId(userId)
                .roleId(role.getId())
                .build();
        userRoleRepository.save(userRoleEntity);

        UserRegisteredEvent userRegisteredEvent = UserRegisteredEvent.builder()
                .userId(userId)
                .username(signupRequest.getUsername())
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .registeredAt(LocalDateTime.now())
                .build();
//        userRegisteredProducer.sendUserRegistrationEvent(userRegisteredEvent);

        SignUpResponse signUpResponse = new SignUpResponse(userId, signupRequest.getUsername());
        lock.unlock();
        log.info("User with id {} has been registered successfully", userId);
        return RestfulResponse.success(signUpResponse, ResponseStatus.SUCCESS);

    }

    private String generateAccessToken(AuthenticationDto userDetails) {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(JWT_EXPIRATION_TIME, ChronoUnit.MILLIS))
                .subject(String.valueOf(userDetails.getUserId()))
                .claim("scope", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" ")))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

    private String generateRefreshToken(AuthenticationDto userDetails) {

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(REFRESH_EXPIRATION_TIME, ChronoUnit.MILLIS))
                .subject(userDetails.getUsername())
                .claim("type", "refresh")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

}
