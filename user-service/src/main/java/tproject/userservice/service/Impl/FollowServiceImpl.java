package tproject.userservice.service.Impl;

import org.springframework.stereotype.Service;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.request.follow.FollowRequestDto;
import tproject.userservice.dto.request.follow.GetFollowerRequestDto;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.dto.response.follow.GetFollowerResponseDto;
import tproject.userservice.dto.response.follow.GetFollowingResponseDto;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.enumeration.FollowStatus;
import tproject.userservice.mapper.FollowMapper;
import tproject.userservice.repository.FollowRepository;
import tproject.userservice.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;

    private final FollowRepository followRepository;

    public FollowServiceImpl(FollowMapper followMapper, FollowRepository followRepository) {
        this.followMapper = followMapper;
        this.followRepository = followRepository;
    }

    @Override
    public RestfulResponse<FollowResponseDto> followUser(FollowRequestDto followRequestDto) {
        FollowEntity followEntity = followMapper.followRequestToEntity(followRequestDto);
        followEntity.setStatus(FollowStatus.FOLLOWING);
        FollowResponseDto followResponse = followMapper.entityToFollowResponse(followRepository.save(followEntity));
        return RestfulResponse.success(followResponse, ResponseStatus.SUCCESS);
    }

    @Override
    public RestfulResponse<FollowResponseDto> unfollowUser(FollowRequestDto followRequestDto) {
        FollowEntity followEntity = followMapper.followRequestToEntity(followRequestDto);
        followEntity.setStatus(FollowStatus.NOT_FOLLOWING);
        FollowResponseDto followResponse = followMapper.entityToFollowResponse(followRepository.save(followEntity));
        return RestfulResponse.success(followResponse, ResponseStatus.SUCCESS);
    }

    @Override
    public RestfulResponse<GetFollowerResponseDto> getFollowers(GetFollowerRequestDto getFollowerRequestDto) {
        return null;
    }

    @Override
    public RestfulResponse<GetFollowingResponseDto> getFollowings(GetFollowerRequestDto getFollowingRequestDto) {
        return null;
    }
}

