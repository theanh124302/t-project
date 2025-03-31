package tproject.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.dto.response.follow.FollowUserDto;
import tproject.userservice.dto.response.follow.GetFollowerResponseDto;
import tproject.userservice.dto.response.follow.GetFollowingResponseDto;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.enumeration.FollowStatus;
import tproject.userservice.enumeration.UserResponseMessage;
import tproject.userservice.repository.FollowReadOnlyRepository;
import tproject.userservice.repository.FollowRepository;
import tproject.userservice.service.FollowService;

import java.sql.Timestamp;
import java.util.Optional;



@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

    private static final Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);

    private final FollowRepository followRepository;

    private final FollowReadOnlyRepository followReadOnlyRepository;

    @Override
    @Transactional
    public RestfulResponse<FollowResponseDto> followUser(Long userId, Long actorId) {
        log.info("Follow request: userId={}, actorId={}", userId, actorId);

        if (userId == null || actorId == null) {
            return RestfulResponse.error(UserResponseMessage.USER_ID_CANNOT_BE_NULL, ResponseStatus.ERROR);
        }

        if (userId.equals(actorId)) {
            return RestfulResponse.error(UserResponseMessage.CANNOT_FOLLOW_YOURSELF, ResponseStatus.ERROR);
        }

        Optional<FollowEntity> existingFollow = followRepository
                .findBySourceUserIdAndTargetUserId(actorId, userId);

        FollowEntity followEntity;

        if (existingFollow.isPresent()) {
            followEntity = existingFollow.get();
            followEntity.setStatus(FollowStatus.FOLLOWING);
        } else {
            followEntity = FollowEntity.builder()
                    .sourceUserId(actorId)
                    .targetUserId(userId)
                    .status(FollowStatus.FOLLOWING)
                    .followDate(new Timestamp(System.currentTimeMillis()))
                    .build();
        }

        FollowEntity savedEntity = followRepository.save(followEntity);
        FollowResponseDto followResponse = FollowResponseDto.builder()
                .sourceUserId(savedEntity.getSourceUserId())
                .targetUserId(savedEntity.getTargetUserId())
                .status(savedEntity.getStatus())
                .build();

        return RestfulResponse.success(followResponse, ResponseStatus.SUCCESS);
    }

    @Override
    @Transactional
    public RestfulResponse<FollowResponseDto> unfollowUser(Long userId, Long actorId) {
        log.info("Unfollow request: userId={}, actorId={}", userId, actorId);

        if (userId == null || actorId == null) {
            return RestfulResponse.error(UserResponseMessage.USER_ID_CANNOT_BE_NULL, ResponseStatus.ERROR);
        }

        Optional<FollowEntity> existingFollow = followRepository
                .findBySourceUserIdAndTargetUserId(actorId, userId);

        if (existingFollow.isEmpty()) {
            return RestfulResponse.error(UserResponseMessage.FOLLOW_NOT_FOUND, ResponseStatus.ERROR);
        }

        FollowEntity followEntity = existingFollow.get();
        followEntity.setStatus(FollowStatus.NOT_FOLLOWING);

        FollowEntity savedEntity = followRepository.save(followEntity);
        FollowResponseDto followResponse = FollowResponseDto.builder()
                .sourceUserId(savedEntity.getSourceUserId())
                .targetUserId(savedEntity.getTargetUserId())
                .status(savedEntity.getStatus())
                .build();

        return RestfulResponse.success(followResponse, ResponseStatus.SUCCESS);
    }

    @Override
    public RestfulResponse<GetFollowerResponseDto> getFollowers(Long userId, Long actorId, Pageable pageable) {
        Page<FollowUserDto> followUsers = followReadOnlyRepository
                .findFollowersByUserIdAndStatus(userId, FollowStatus.FOLLOWING, pageable);
        return RestfulResponse.success(new GetFollowerResponseDto(followUsers), ResponseStatus.SUCCESS);
    }

    @Override
    public RestfulResponse<GetFollowingResponseDto> getFollowings(Long userId, Long actorId, Pageable pageable) {
        Page<FollowUserDto> followUsers = followReadOnlyRepository
                .findFollowingsByUserIdAndStatus(userId, FollowStatus.FOLLOWING, pageable);
        return RestfulResponse.success(new GetFollowingResponseDto(followUsers), ResponseStatus.SUCCESS);
    }

}
