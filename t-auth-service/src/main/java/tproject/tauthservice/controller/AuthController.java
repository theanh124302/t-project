package tproject.tauthservice.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tauthservice.dto.request.LoginRequest;
import tproject.tauthservice.dto.request.SignupRequest;
import tproject.tauthservice.dto.response.JwtResponse;
import tproject.tauthservice.dto.response.SignUpResponse;
import tproject.tauthservice.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser( @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        try {
            SignUpResponse user = authService.registerUser(signupRequest);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException("Error: Username is already taken!");
        }
    }

    @GetMapping("/authorization-success")
    public ResponseEntity<String> authorizationSuccess() {
        return ResponseEntity.ok("Authorization successful");
    }



}
