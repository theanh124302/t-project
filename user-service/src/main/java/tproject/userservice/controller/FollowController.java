package tproject.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.dto.response.follow.GetFollowerResponseDto;
import tproject.userservice.dto.response.follow.GetFollowingResponseDto;
import tproject.userservice.service.FollowService;

@RestController
@RequestMapping("/follow/v1")
@AllArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PutMapping("/{userId}")
    public RestfulResponse<FollowResponseDto> follow(@PathVariable Long userId, Jwt jwt) {
        return followService.followUser(userId, Long.parseLong(jwt.getSubject()));
    }

    @PutMapping("/unfollow/{userId}")
    public RestfulResponse<FollowResponseDto> unfollow(@PathVariable Long userId, Jwt jwt) {
        return followService.unfollowUser(userId, Long.parseLong(jwt.getSubject()));
    }

    @GetMapping("/get-followers/{userId}")
    public RestfulResponse<GetFollowerResponseDto> getFollowers(@PathVariable Long userId,
                                                                Jwt jwt,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return followService.getFollowers(userId, Long.parseLong(jwt.getSubject()), pageable);
    }

    @GetMapping("/get-followings/{userId}")
    public RestfulResponse<GetFollowingResponseDto> getFollowings(@PathVariable Long userId,
                                                                    Jwt jwt,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return followService.getFollowings(userId, Long.parseLong(jwt.getSubject()), pageable);
    }

}
