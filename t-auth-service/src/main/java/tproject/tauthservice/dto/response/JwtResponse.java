package tproject.tauthservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {
    private String token;
    private String tokenType;
    private String refreshToken;
    private Long id;
    private String username;
    private List<String> roles;
    private int expiresIn;
}
