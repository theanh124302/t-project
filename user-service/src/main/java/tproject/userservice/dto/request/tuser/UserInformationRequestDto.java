package tproject.userservice.dto.request.tuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class UserInformationRequestDto {

    @NonNull
    private Long userId;

    @NonNull
    private Long fromUserId;

}
