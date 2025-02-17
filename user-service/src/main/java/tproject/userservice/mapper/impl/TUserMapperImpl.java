package tproject.userservice.mapper.impl;

import org.springframework.stereotype.Component;
import tproject.userservice.dto.request.tuser.UserRegisterRequestDto;
import tproject.userservice.dto.request.tuser.UserUpdateRequestDto;
import tproject.userservice.dto.response.tuser.UserInformationResponseDto;
import tproject.userservice.dto.response.tuser.UserRegisterResponseDto;
import tproject.userservice.dto.response.tuser.UserUpdateResponseDto;
import tproject.userservice.entity.TUserEntity;
import tproject.userservice.mapper.TUserMapper;

@Component
public class TUserMapperImpl implements TUserMapper {

    @Override
    public TUserEntity userRegisterRequestToEntity(UserRegisterRequestDto dto) {
        return TUserEntity.builder()
                .authId(dto.getAuthId())
                .bio(dto.getBio())
                .avatarUrl(dto.getAvatarUrl())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .build();
    }

    @Override
    public UserRegisterResponseDto entityToUserRegisterResponse(TUserEntity entity) {
        return UserRegisterResponseDto.builder()
                .bio(entity.getBio())
                .avatarUrl(entity.getAvatarUrl())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .build();
    }

    @Override
    public TUserEntity userUpdateRequestToEntity(UserUpdateRequestDto dto) {
        return TUserEntity.builder()
                .bio(dto.getBio())
                .avatarUrl(dto.getAvatarUrl())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .build();
    }

    @Override
    public UserUpdateResponseDto entityToUserUpdateResponse(TUserEntity entity) {
        return UserUpdateResponseDto.builder()
                .bio(entity.getBio())
                .avatarUrl(entity.getAvatarUrl())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .build();
    }

    @Override
    public UserInformationResponseDto entityToUserInformationResponse(TUserEntity entity) {
        return UserInformationResponseDto.builder()
                .bio(entity.getBio())
                .avatarUrl(entity.getAvatarUrl())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .build();
    }


}
