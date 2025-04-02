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
import tproject.tcommon.enums.ResponseStatus;
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
        Long currentUserId = Long.parseLong(jwt.getSubject());
        FollowResponseDto responseDto = followService.followUser(userId, currentUserId);
        return RestfulResponse.success(responseDto, ResponseStatus.SUCCESS);
    }

    @PutMapping("/unfollow/{userId}")
    public RestfulResponse<FollowResponseDto> unfollow(@PathVariable Long userId, Jwt jwt) {
        Long currentUserId = Long.parseLong(jwt.getSubject());
        FollowResponseDto responseDto = followService.unfollowUser(userId, currentUserId);
        return RestfulResponse.success(responseDto, ResponseStatus.SUCCESS);
    }

    @GetMapping("/get-followers/{userId}")
    public RestfulResponse<GetFollowerResponseDto> getFollowers(
            @PathVariable Long userId,
            Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long currentUserId = Long.parseLong(jwt.getSubject());
        Pageable pageable = PageRequest.of(page, size);
        GetFollowerResponseDto responseDto = followService.getFollowers(userId, currentUserId, pageable);
        return RestfulResponse.success(responseDto, ResponseStatus.SUCCESS);
    }

    @GetMapping("/get-followings/{userId}")
    public RestfulResponse<GetFollowingResponseDto> getFollowings(
            @PathVariable Long userId,
            Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long currentUserId = Long.parseLong(jwt.getSubject());
        Pageable pageable = PageRequest.of(page, size);
        GetFollowingResponseDto responseDto = followService.getFollowings(userId, currentUserId, pageable);
        return RestfulResponse.success(responseDto, ResponseStatus.SUCCESS);
    }
}