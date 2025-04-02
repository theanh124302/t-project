package tproject.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.dto.response.follow.FollowUserDto;
import tproject.userservice.dto.response.follow.GetFollowerResponseDto;
import tproject.userservice.dto.response.follow.GetFollowingResponseDto;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.enumeration.FollowStatus;
import tproject.userservice.enumeration.UserResponseMessage;
import tproject.userservice.exception.FollowException;
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
    public FollowResponseDto followUser(Long userId, Long actorId) {
        log.info("Follow request: userId={}, actorId={}", userId, actorId);

        if (userId == null || actorId == null) {
            throw new FollowException(UserResponseMessage.USER_ID_CANNOT_BE_NULL);
        }

        if (userId.equals(actorId)) {
            throw new FollowException(UserResponseMessage.CANNOT_FOLLOW_YOURSELF);
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
        return FollowResponseDto.builder()
                .sourceUserId(savedEntity.getSourceUserId())
                .targetUserId(savedEntity.getTargetUserId())
                .status(savedEntity.getStatus())
                .build();
    }

    @Override
    @Transactional
    public FollowResponseDto unfollowUser(Long userId, Long actorId) {
        log.info("Unfollow request: userId={}, actorId={}", userId, actorId);

        if (userId == null || actorId == null) {
            throw new FollowException(UserResponseMessage.USER_ID_CANNOT_BE_NULL);
        }

        Optional<FollowEntity> existingFollow = followRepository
                .findBySourceUserIdAndTargetUserId(actorId, userId);

        if (existingFollow.isEmpty()) {
            throw new FollowException(UserResponseMessage.FOLLOW_NOT_FOUND);
        }

        FollowEntity followEntity = existingFollow.get();
        followEntity.setStatus(FollowStatus.NOT_FOLLOWING);

        FollowEntity savedEntity = followRepository.save(followEntity);
        return FollowResponseDto.builder()
                .sourceUserId(savedEntity.getSourceUserId())
                .targetUserId(savedEntity.getTargetUserId())
                .status(savedEntity.getStatus())
                .build();
    }

    @Override
    public GetFollowerResponseDto getFollowers(Long userId, Long actorId, Pageable pageable) {
        Page<FollowUserDto> followUsers = followReadOnlyRepository
                .findFollowersByUserIdAndStatus(userId, FollowStatus.FOLLOWING, pageable);
        return new GetFollowerResponseDto(followUsers);
    }

    @Override
    public GetFollowingResponseDto getFollowings(Long userId, Long actorId, Pageable pageable) {
        Page<FollowUserDto> followUsers = followReadOnlyRepository
                .findFollowingsByUserIdAndStatus(userId, FollowStatus.FOLLOWING, pageable);
        return new GetFollowingResponseDto(followUsers);
    }
}