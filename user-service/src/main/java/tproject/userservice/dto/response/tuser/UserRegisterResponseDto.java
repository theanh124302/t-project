package tproject.userservice.dto.response.tuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class UserRegisterResponseDto {

    private String username;

    private String firstName;

    private String lastName;

    private String bio;

    private String avatarUrl;

}
