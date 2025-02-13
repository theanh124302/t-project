package tproject.userservice.service;

import tproject.userservice.dto.request.UserInformationRequestDto;
import tproject.userservice.dto.request.UserRegisterRequestDto;
import tproject.userservice.dto.request.UserSearchRequestDto;
import tproject.userservice.dto.request.UserUpdateRequestDto;
import tproject.userservice.dto.response.UserInformationResponseDto;
import tproject.userservice.dto.response.UserRegisterResponseDto;
import tproject.userservice.dto.response.UserUpdateResponseDto;

public interface TUserService {

    UserRegisterResponseDto registerUser(UserRegisterRequestDto userRegisterRequestDto);

    UserUpdateResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto);

    UserInformationResponseDto getUserInformation(UserInformationRequestDto userInformationRequestDto);

    UserSearchRequestDto searchUser(UserSearchRequestDto userSearchRequestDto);

}
