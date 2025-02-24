package tproject.userservice.dto.response.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import tproject.userservice.enumeration.FollowStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class FollowResponseDto {

    private Long id;

    private String followerUsername;

    private String followingUsername;

    private FollowStatus status;

}
