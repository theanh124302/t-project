package tproject.userservice.service;

import org.springframework.data.domain.Pageable;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.request.follow.FollowRequestDto;
import tproject.userservice.dto.request.follow.GetFollowerRequestDto;
import tproject.userservice.dto.request.follow.GetFollowingRequestDto;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.dto.response.follow.GetFollowerResponseDto;
import tproject.userservice.dto.response.follow.GetFollowingResponseDto;

public interface FollowService {
    RestfulResponse<FollowResponseDto> followUser(Long userId, Long actorId);
    RestfulResponse<FollowResponseDto> unfollowUser(Long userId, Long actorId);
    RestfulResponse<GetFollowerResponseDto> getFollowers(Long userId, Long actorId, Pageable pageable);
    RestfulResponse<GetFollowingResponseDto> getFollowings(Long userId, Long actorId, Pageable pageable);
}
