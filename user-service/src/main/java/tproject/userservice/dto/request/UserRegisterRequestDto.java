package tproject.userservice.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDto {

    @NonNull
    private Long authId;

    @NonNull
    private String username;

    @NonNull
    private String fullName;

    private String bio;

    private String avatarUrl;

}
