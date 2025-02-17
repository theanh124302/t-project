package tproject.userservice.dto.request;

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
public class UserStatsRequestDto {

    private Long userId;

    private Long fromUserId;

}
