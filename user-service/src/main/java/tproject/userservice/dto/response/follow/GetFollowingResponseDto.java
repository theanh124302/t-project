package tproject.userservice.dto.response.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetFollowingResponseDto {

    private List<FollowUserDto> following;

}
