package tproject.userservice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformationRequestDto {
    private Long userId;
    private Long fromUserId;
}
