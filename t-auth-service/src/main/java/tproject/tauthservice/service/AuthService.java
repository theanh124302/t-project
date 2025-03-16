package tproject.tauthservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    private static final int JWT_EXPIRATION_TIME = 3600000; // 1 hour
    private static final int REFRESH_EXPIRATION_TIME = 9000000; // 2.5 hours
    private final AuthenticationRepository authenticationRepository;
    private final UserRoleRepository userRoleRepository;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));



        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthenticationDto userDetails = (AuthenticationDto) authentication.getPrincipal();

        log.info("User {} has been authenticated", userDetails);

        String accessToken = generateAccessToken(userDetails);
        String refreshToken = generateRefreshToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JwtResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .id(userDetails.getUserId())
                .username(userDetails.getUsername())
                .roles(roles)
                .expiresIn(JWT_EXPIRATION_TIME / 1000)
                .build();
    }

    @Transactional
    public SignUpResponse registerUser(SignupRequest signupRequest) {

        UserEntity userEntity = userRepository.findByUsername(signupRequest.getUsername()).orElse(new UserEntity());

        userEntity.setFirstName(signupRequest.getFirstName());
        userEntity.setLastName(signupRequest.getLastName());
        userEntity.setUsername(signupRequest.getUsername());
        Long userId = userRepository.save(userEntity).getId();

        AuthenticationEntity authenticationEntity = authenticationRepository.findByUsername(signupRequest.getUsername())
                .orElse(new AuthenticationEntity());
        authenticationEntity.setUserId(userId);
        authenticationEntity.setUsername(signupRequest.getUsername());
        authenticationEntity.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        authenticationRepository.save(authenticationEntity).getId();

        List<RoleEntity> roles = roleRepository.findAllByNameIn(signupRequest.getRoles());

        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        for (RoleEntity role : roles) {
            userRoleEntities.add(UserRoleEntity.builder()
                    .userId(userId)
                    .roleId(role.getId())
                    .build());
        }

        userRoleRepository.saveAll(userRoleEntities);

        return new SignUpResponse(userId, signupRequest.getUsername());
    }

    private String generateAccessToken(AuthenticationDto userDetails) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(JWT_EXPIRATION_TIME, ChronoUnit.MILLIS))
                .subject(userDetails.getUsername())
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
