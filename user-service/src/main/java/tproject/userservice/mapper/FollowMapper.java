package tproject.userservice.mapper;

import tproject.userservice.dto.request.follow.FollowRequestDto;
import tproject.userservice.dto.response.follow.FollowAcceptResponseDto;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.entity.FollowEntity;

public interface FollowMapper {

    FollowEntity followRequestToEntity(FollowRequestDto request);

    FollowResponseDto entityToFollowResponse(FollowEntity entity);

    FollowAcceptResponseDto entityToFollowAcceptResponse(FollowEntity entity);

}
