package tproject.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.message.ResponseMessage;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.request.tuser.UserInformationRequestDto;
import tproject.userservice.dto.request.tuser.UserRegisterRequestDto;
import tproject.userservice.dto.request.tuser.UserSearchRequestDto;
import tproject.userservice.dto.request.tuser.UserUpdateRequestDto;
import tproject.userservice.dto.response.tuser.UserInformationResponseDto;
import tproject.userservice.dto.response.tuser.UserRegisterResponseDto;
import tproject.userservice.dto.response.tuser.UserUpdateResponseDto;
import tproject.userservice.entity.TUserEntity;
import tproject.userservice.enumeration.UserResponseMessage;
import tproject.userservice.mapper.TUserMapper;
import tproject.userservice.repository.command.TUserRepository;
import tproject.userservice.service.TUserService;

@Service
@AllArgsConstructor
public class TUserServiceImpl implements TUserService {

    private static final Logger log = LoggerFactory.getLogger(TUserServiceImpl.class);

    private final TUserMapper tUserMapper;

    private final TUserRepository tUserRepository;

    @Override
    public RestfulResponse<UserRegisterResponseDto> registerUser(UserRegisterRequestDto userRegisterRequestDto) {

        if (tUserRepository.findByUsername(userRegisterRequestDto.getUsername()).isPresent()) {
            log.error("Username already exists: {}", userRegisterRequestDto.getUsername());
            return RestfulResponse.error(UserResponseMessage.USERNAME_ALREADY_EXISTS, ResponseStatus.ERROR);
        }

        TUserEntity tUserEntity = tUserMapper.userRegisterRequestToEntity(userRegisterRequestDto);
        TUserEntity savedUser = tUserRepository.save(tUserEntity);
        UserRegisterResponseDto userRegisterResponseDto = tUserMapper.entityToUserRegisterResponse(savedUser);
        return RestfulResponse.success(userRegisterResponseDto, ResponseStatus.SUCCESS);

    }

    @Override
    public RestfulResponse<UserUpdateResponseDto> updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        Long userId = userUpdateRequestDto.getId();
        TUserEntity tUserEntity = tUserRepository.findById(userId).orElse(null);
        if (tUserEntity == null) {
            log.error("User not found with id: {}", userId);
            return RestfulResponse.error(ResponseMessage.NOT_FOUND, ResponseStatus.ERROR);
        }
        tUserEntity.setBio(userUpdateRequestDto.getBio());
        tUserEntity.setAvatarUrl(userUpdateRequestDto.getAvatarUrl());
        tUserEntity.setFirstName(userUpdateRequestDto.getFirstName());
        tUserEntity.setLastName(userUpdateRequestDto.getLastName());
        tUserEntity.setUsername(userUpdateRequestDto.getUsername());
        TUserEntity updatedUser = tUserRepository.save(tUserEntity);
        UserUpdateResponseDto userUpdateResponseDto = tUserMapper.entityToUserUpdateResponse(updatedUser);
        return RestfulResponse.success(userUpdateResponseDto, ResponseStatus.SUCCESS);
    }

    @Override
    public RestfulResponse<UserInformationResponseDto> getUserInformation(UserInformationRequestDto userInformationRequestDto) {
        log.info("Get user information with body: {}", userInformationRequestDto);
        String username = userInformationRequestDto.getUsername();
        TUserEntity tUserEntity = tUserRepository.findByUsername(username).orElse(null);
        if (tUserEntity == null) {
            log.error("User not found with username: {}", username);
            return RestfulResponse.error(ResponseMessage.NOT_FOUND, ResponseStatus.ERROR);
        }
        UserInformationResponseDto userInformationResponseDto = tUserMapper.entityToUserInformationResponse(tUserEntity);
        log.info("User information: {}", userInformationResponseDto);
        return RestfulResponse.success(userInformationResponseDto, ResponseStatus.SUCCESS);
    }

    @Override
    public RestfulResponse<UserSearchRequestDto> searchUser(UserSearchRequestDto userSearchRequestDto) {
        return null;
    }


}
