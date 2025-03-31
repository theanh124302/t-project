package tproject.userservice.dto.response.privacy;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.userservice.enumeration.UserPrivacy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrivacyResponseDto {

    private Long userId;

    private UserPrivacy privacy;

}
