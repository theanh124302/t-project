package tproject.userservice.dto.response.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FollowUserDto {

    private Long username;

    private String name;

    private String profileImage;

}
