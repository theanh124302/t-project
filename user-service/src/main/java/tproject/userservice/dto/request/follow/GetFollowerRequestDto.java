package tproject.userservice.dto.request.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetFollowerRequestDto {

    private Long userId;

    private Long actorId;

}
