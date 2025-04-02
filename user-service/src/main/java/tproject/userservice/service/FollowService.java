package tproject.userservice.service;

import org.springframework.data.domain.Pageable;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.dto.response.follow.GetFollowerResponseDto;
import tproject.userservice.dto.response.follow.GetFollowingResponseDto;

public interface FollowService {
    FollowResponseDto followUser(Long userId, Long actorId);
    FollowResponseDto unfollowUser(Long userId, Long actorId);
    GetFollowerResponseDto getFollowers(Long userId, Long actorId, Pageable pageable);
    GetFollowingResponseDto getFollowings(Long userId, Long actorId, Pageable pageable);
}