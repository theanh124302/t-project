package tproject.userservice.service;

import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.request.follow.FollowRequestDto;
import tproject.userservice.dto.request.follow.GetFollowerRequestDto;
import tproject.userservice.dto.request.follow.GetFollowingRequestDto;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.dto.response.follow.GetFollowerResponseDto;
import tproject.userservice.dto.response.follow.GetFollowingResponseDto;

public interface FollowService {
    RestfulResponse<FollowResponseDto> followUser(FollowRequestDto followRequestDto);
    RestfulResponse<FollowResponseDto> unfollowUser(FollowRequestDto followRequestDto);
    RestfulResponse<GetFollowerResponseDto> getFollowers(GetFollowerRequestDto getFollowerRequestDto);
    RestfulResponse<GetFollowingResponseDto> getFollowings(GetFollowingRequestDto getFollowingRequestDto);
}
