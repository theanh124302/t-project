package tproject.tauthservice.controller;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.restfulresponse.RestfulResponse;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/signin")
    public RestfulResponse<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        log.info("Call api /api/v1/auth/signin with request: {}", loginRequest);
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public RestfulResponse<SignUpResponse> registerUser(@RequestBody SignupRequest signupRequest) throws InterruptedException {
        log.info("Call api /api/v1/auth/signup with request: {}", signupRequest);
        return authService.registerUser(signupRequest);
    }

    @GetMapping("/authorization-success")
    public ResponseEntity<String> authorizationSuccess() {
        return ResponseEntity.ok("Authorization successful");
    }

}
