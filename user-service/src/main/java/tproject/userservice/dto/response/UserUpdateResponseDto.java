package tproject.userservice.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserUpdateResponseDto {

    private Long id;

    private String username;

    private String fullName;

    private String bio;

    private String avatarUrl;

}
