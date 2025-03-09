package tproject.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.authservice.dto.AuthenticationResponseDTO;
//import tproject.authservice.dto.RefreshTokenRequestDTO;
import tproject.authservice.dto.SignInRequestDTO;
import tproject.authservice.dto.SignUpRequestDTO;
import tproject.authservice.entity.User;
import tproject.authservice.service.AuthenticationService;

@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequestDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO){
        return ResponseEntity.ok(authenticationService.signIn(signInRequestDTO));
    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<AuthenticationResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
//        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequestDTO));
//    }
}
