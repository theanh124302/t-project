package tproject.userservice.dto.request.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetFollowingRequestDto {

    private String username;

    private String actorUsername;

}