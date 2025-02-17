package tproject.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class UserPrivacySettingRequestDto {

    Long userId;

    Boolean isPrivate;

}
