package tproject.userservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.request.follow.FollowRequestDto;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.service.FollowService;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow")
    public RestfulResponse<FollowResponseDto> follow(@RequestBody FollowRequestDto followRequestDto) {
        return followService.followUser(followRequestDto);
    }

    @PostMapping("/unfollow")
    public RestfulResponse<FollowResponseDto> unfollow(@RequestBody FollowRequestDto followRequestDto) {
        return followService.unfollowUser(followRequestDto);
    }

}
