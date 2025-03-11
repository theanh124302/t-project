package tproject.oauthresourceserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {
    @GetMapping("/public/message")
    public Map<String, String> publicMessage() {
        return Collections.singletonMap("message", "Đây là API công khai");
    }

    @GetMapping("/private/message")
    public Map<String, Object> privateMessage(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "message", "Đây là API được bảo vệ",
                "principal", jwt.getClaims(),
                "headers", jwt.getHeaders()
        );
    }
}