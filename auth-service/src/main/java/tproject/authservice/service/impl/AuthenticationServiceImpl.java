package tproject.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tproject.authservice.dto.AuthUserDTO;
import tproject.authservice.dto.AuthenticationResponseDTO;
import tproject.authservice.dto.SignInRequestDTO;
import tproject.authservice.dto.SignUpRequestDTO;
import tproject.authservice.entity.User;
import tproject.authservice.repository.UserRepository;
import tproject.authservice.service.AuthenticationService;
import tproject.authservice.service.JwtService;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDTO signIn(SignInRequestDTO request) {
        System.out.println(request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        AuthUserDTO user = userRepository.findByUsername(request.getUsername()).map(AuthUserDTO::new).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        var jwt = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setToken(jwt);
//        authenticationResponseDTO.setRefreshToken(refreshToken);
        return authenticationResponseDTO;
    }

    @Override
    public User signUp(SignUpRequestDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }
//
//    public AuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO){
//        String username = jwtService.extractUserName(refreshTokenRequestDTO.getToken());
//        AuthUserDTO user = userRepository.findByUsername(username).map(AuthUserDTO::new).orElseThrow();
//        if(jwtService.isTokenValid(refreshTokenRequestDTO.getToken(),user)){
//            var jwt = jwtService.generateToken(user);
//            AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
//            authenticationResponseDTO.setToken(jwt);
//            authenticationResponseDTO.setRefreshToken(refreshTokenRequestDTO.getToken());
//            return authenticationResponseDTO;
//        }
//        return null;
//    }
}

