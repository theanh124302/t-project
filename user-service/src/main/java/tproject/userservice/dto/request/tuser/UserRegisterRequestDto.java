package tproject.userservice.dto.request.tuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class UserRegisterRequestDto {

    @NonNull
    private Long authId;

    @NonNull
    private String username;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String bio;

    private String avatarUrl;

}
