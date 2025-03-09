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
import tproject.userservice.dto.request.follow.FollowRequestDto;
import tproject.userservice.dto.request.follow.GetFollowerRequestDto;
import tproject.userservice.dto.request.follow.GetFollowingRequestDto;
import tproject.userservice.dto.response.follow.FollowResponseDto;
import tproject.userservice.dto.response.follow.FollowUserDto;
import tproject.userservice.dto.response.follow.GetFollowerResponseDto;
import tproject.userservice.dto.response.follow.GetFollowingResponseDto;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.enumeration.FollowStatus;
import tproject.userservice.mapper.FollowMapper;
import tproject.userservice.repository.command.FollowRepository;
import tproject.userservice.repository.command.TUserRepository;
import tproject.userservice.repository.query.FollowReadOnlyRepository;
import tproject.userservice.service.FollowService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import tproject.userservice.enumeration.UserResponseMessage;



@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

    private static final Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);
    private final FollowMapper followMapper;

    private final FollowRepository followRepository;

    private final FollowReadOnlyRepository followReadOnlyRepository;

    private final TUserRepository tUserRepository;

    @Override
    @Transactional
    public RestfulResponse<FollowResponseDto> followUser(FollowRequestDto followRequestDto) {
        log.info("Follow request: {}", followRequestDto);

        if (followRequestDto.getFollowerUsername() == null || followRequestDto.getFollowingUsername() == null) {
            return RestfulResponse.error(UserResponseMessage.USERNAME_CANNOT_BE_NULL, ResponseStatus.ERROR);
        }

        if (followRequestDto.getFollowerUsername().equals(followRequestDto.getFollowingUsername())) {
            return RestfulResponse.error(UserResponseMessage.CANNOT_FOLLOW_YOURSELF, ResponseStatus.ERROR);
        }

        Optional<FollowEntity> existingFollow = followRepository
                .findByFollowerUsernameAndFollowingUsername(
                        followRequestDto.getFollowerUsername(),
                        followRequestDto.getFollowingUsername());

        FollowEntity followEntity;

        if (existingFollow.isPresent()) {
            followEntity = existingFollow.get();
            followEntity.setStatus(FollowStatus.FOLLOWING);
        } else {
            followEntity = FollowEntity.builder()
                    .followerUsername(followRequestDto.getFollowerUsername())
                    .followingUsername(followRequestDto.getFollowingUsername())
                    .status(FollowStatus.FOLLOWING)
                    .followDate(new Timestamp(System.currentTimeMillis()))
                    .build();
        }

        FollowEntity savedEntity = followRepository.save(followEntity);
        FollowResponseDto followResponse = followMapper.entityToFollowResponse(savedEntity);

        return RestfulResponse.success(followResponse, ResponseStatus.SUCCESS);
    }

    @Override
    @Transactional
    public RestfulResponse<FollowResponseDto> unfollowUser(FollowRequestDto followRequestDto) {
        log.info("Unfollow request: {}", followRequestDto);

        if (followRequestDto.getFollowerUsername() == null || followRequestDto.getFollowingUsername() == null) {
            return RestfulResponse.error(UserResponseMessage.USERNAME_CANNOT_BE_NULL, ResponseStatus.ERROR);
        }

        Optional<FollowEntity> existingFollow = followRepository
                .findByFollowerUsernameAndFollowingUsername(
                        followRequestDto.getFollowerUsername(),
                        followRequestDto.getFollowingUsername());

        if (existingFollow.isEmpty()) {
            return RestfulResponse.error(UserResponseMessage.FOLLOW_NOT_FOUND, ResponseStatus.ERROR);
        }

        FollowEntity followEntity = existingFollow.get();
        followEntity.setStatus(FollowStatus.NOT_FOLLOWING);

        FollowEntity savedEntity = followRepository.save(followEntity);
        FollowResponseDto followResponse = followMapper.entityToFollowResponse(savedEntity);

        return RestfulResponse.success(followResponse, ResponseStatus.SUCCESS);
    }

    @Override
    public RestfulResponse<GetFollowerResponseDto> getFollowers(GetFollowerRequestDto getFollowerRequestDto, Pageable pageable) {
        Page<FollowUserDto> followUsers = followReadOnlyRepository
                .findFollowersByUserIdAndStatus(getFollowerRequestDto.getUsername(), FollowStatus.FOLLOWING, pageable);
        return RestfulResponse.success(new GetFollowerResponseDto(followUsers), ResponseStatus.SUCCESS);
    }

    @Override
    public RestfulResponse<GetFollowingResponseDto> getFollowings(GetFollowingRequestDto getFollowingRequestDto, Pageable pageable) {
        Page<FollowUserDto> followUsers = followReadOnlyRepository
                .findFollowingsByUserIdAndStatus(getFollowingRequestDto.getUsername(), FollowStatus.FOLLOWING, pageable);
        return RestfulResponse.success(new GetFollowingResponseDto(followUsers), ResponseStatus.SUCCESS);
    }

}

