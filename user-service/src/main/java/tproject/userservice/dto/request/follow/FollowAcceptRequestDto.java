package tproject.userservice.dto.request.follow;

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
public class FollowAcceptRequestDto {

    private Long followId;

}
