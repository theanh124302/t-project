package tproject.userservice.mapper.impl;

import org.springframework.stereotype.Component;
import tproject.userservice.dto.request.UserInformationRequestDto;
import tproject.userservice.dto.request.UserRegisterRequestDto;
import tproject.userservice.dto.request.UserUpdateRequestDto;
import tproject.userservice.dto.response.UserInformationResponseDto;
import tproject.userservice.dto.response.UserRegisterResponseDto;
import tproject.userservice.dto.response.UserUpdateResponseDto;
import tproject.userservice.entity.TUserEntity;
import tproject.userservice.mapper.TUserMapper;

@Component
public class TUserMapperImpl implements TUserMapper {

    public TUserEntity UserRegisterRequestToEntity(UserRegisterRequestDto dto) {
        return TUserEntity.builder()
                .authId(dto.getAuthId())
                .bio(dto.getBio())
                .avatarUrl(dto.getAvatarUrl())
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .build();
    }

    public UserRegisterResponseDto EntityToUserRegisterResponse(TUserEntity entity) {
        return UserRegisterResponseDto.builder()
                .id(entity.getId())
                .bio(entity.getBio())
                .avatarUrl(entity.getAvatarUrl())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .build();
    }

    public TUserEntity UserUpdateRequestToEntity(UserUpdateRequestDto dto) {
        return TUserEntity.builder()
                .bio(dto.getBio())
                .avatarUrl(dto.getAvatarUrl())
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .build();
    }

    public UserUpdateResponseDto EntityToUserUpdateResponse(TUserEntity entity) {
        return UserUpdateResponseDto.builder()
                .id(entity.getId())
                .bio(entity.getBio())
                .avatarUrl(entity.getAvatarUrl())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .build();
    }

    public UserInformationResponseDto EntityToUserInformationResponse(TUserEntity entity) {
        return UserInformationResponseDto.builder()
                .id(entity.getId())
                .bio(entity.getBio())
                .avatarUrl(entity.getAvatarUrl())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .build();
    }


}
