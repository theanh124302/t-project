package tproject.userservice.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserUpdateRequestDto {


    @NonNull
    private Long authId;

    @NonNull
    private String username;

    @NonNull
    private String fullName;

    private String bio;

    private String avatarUrl;

}
