package tproject.userservice.dto.request.follow;

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
public class FollowRequestDto {

    @NonNull
    private Long followerId;

    @NonNull
    private Long followingId;

}
