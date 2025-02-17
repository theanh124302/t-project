package tproject.userservice.mapper;

import tproject.userservice.dto.request.tuser.UserRegisterRequestDto;
import tproject.userservice.dto.request.tuser.UserUpdateRequestDto;
import tproject.userservice.dto.response.tuser.UserInformationResponseDto;
import tproject.userservice.dto.response.tuser.UserRegisterResponseDto;
import tproject.userservice.dto.response.tuser.UserUpdateResponseDto;
import tproject.userservice.entity.TUserEntity;

public interface TUserMapper {

    public TUserEntity userRegisterRequestToEntity(UserRegisterRequestDto dto);

    public UserRegisterResponseDto entityToUserRegisterResponse(TUserEntity entity);

    public TUserEntity userUpdateRequestToEntity(UserUpdateRequestDto dto);

    public UserUpdateResponseDto entityToUserUpdateResponse(TUserEntity entity);

    public UserInformationResponseDto entityToUserInformationResponse(TUserEntity entity);

}
