package tproject.userservice.mapper.impl;

import org.springframework.stereotype.Component;
import tproject.userservice.dto.request.follow.FollowRequestDto;
import tproject.userservice.dto.response.follow.FollowAcceptResponseDto;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.mapper.FollowMapper;

@Component
public class FollowMapperImpl implements FollowMapper {

    public FollowEntity followRequestToEntity(FollowRequestDto request) {
        return FollowEntity.builder()
                .followingId(request.getFollowingId())
                .followerId(request.getFollowerId())
                .build();
    }

    public FollowResponseDto entityToFollowResponse(FollowEntity entity) {
        return FollowResponseDto.builder()
                .id(entity.getId())
                .followingId(entity.getFollowingId())
                .followerId(entity.getFollowerId())
                .status(entity.getStatus())
                .build();
    }

    public FollowAcceptResponseDto entityToFollowAcceptResponse(FollowEntity entity) {
        return FollowAcceptResponseDto.builder()
                .followId(entity.getId())
                .status(entity.getStatus())
                .build();
    }

}
