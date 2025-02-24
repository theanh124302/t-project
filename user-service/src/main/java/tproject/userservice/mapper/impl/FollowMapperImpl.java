package tproject.userservice.mapper.impl;

import org.springframework.stereotype.Component;
import tproject.userservice.dto.request.follow.FollowRequestDto;
import tproject.userservice.dto.response.follow.FollowAcceptResponseDto;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.mapper.FollowMapper;

@Component
public class FollowMapperImpl implements FollowMapper {

    public FollowResponseDto entityToFollowResponse(FollowEntity entity) {
        return FollowResponseDto.builder()
                .id(entity.getId())
                .followerUsername(entity.getFollowerUsername())
                .followingUsername(entity.getFollowingUsername())
                .status(entity.getStatus())
                .build();
    }

}
