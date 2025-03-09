package tproject.authservice.service;

import tproject.authservice.dto.AuthenticationResponseDTO;
//import tproject.authservice.dto.RefreshTokenRequestDTO;
import tproject.authservice.dto.SignInRequestDTO;
import tproject.authservice.dto.SignUpRequestDTO;
import tproject.authservice.entity.User;

public interface AuthenticationService {
    AuthenticationResponseDTO signIn(SignInRequestDTO signinRequestDTO);

    User signUp(SignUpRequestDTO signUpRequestDTO);
//    public AuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}