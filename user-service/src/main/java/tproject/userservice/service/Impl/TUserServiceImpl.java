package tproject.userservice.service.Impl;

import org.springframework.stereotype.Service;
import tproject.userservice.dto.request.UserInformationRequestDto;
import tproject.userservice.dto.request.UserRegisterRequestDto;
import tproject.userservice.dto.request.UserSearchRequestDto;
import tproject.userservice.dto.request.UserUpdateRequestDto;
import tproject.userservice.dto.response.UserInformationResponseDto;
import tproject.userservice.dto.response.UserRegisterResponseDto;
import tproject.userservice.dto.response.UserUpdateResponseDto;
import tproject.userservice.entity.TUserEntity;
import tproject.userservice.mapper.impl.TUserMapperImpl;
import tproject.userservice.repository.TUserRepository;
import tproject.userservice.service.TUserService;

@Service
public class TUserServiceImpl implements TUserService {

    private final TUserMapperImpl tUserMapper;

    private final TUserRepository tUserRepository;

    public TUserServiceImpl(TUserRepository tUserRepository, TUserMapperImpl tUserMapper) {
        this.tUserRepository = tUserRepository;
        this.tUserMapper = tUserMapper;
    }

    @Override
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        TUserEntity tUserEntity = tUserMapper.UserRegisterRequestToEntity(userRegisterRequestDto);
        TUserEntity savedUser = tUserRepository.save(tUserEntity);
        return tUserMapper.EntityToUserRegisterResponse(savedUser);
    }

    @Override
    public UserUpdateResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        TUserEntity tUserEntity = tUserMapper.UserUpdateRequestToEntity(userUpdateRequestDto);
        TUserEntity savedUser = tUserRepository.save(tUserEntity);
        return tUserMapper.EntityToUserUpdateResponse(savedUser);
    }

    @Override
    public UserInformationResponseDto getUserInformation(UserInformationRequestDto userInformationRequestDto) {
        TUserEntity tUserEntity = tUserRepository.findById(userInformationRequestDto.getUserId()).orElse(null);
        if (tUserEntity == null) {
            return null;
        }
        return tUserMapper.EntityToUserInformationResponse(tUserEntity);
    }

    @Override
    public UserSearchRequestDto searchUser(UserSearchRequestDto userSearchRequestDto) {
        return null;
    }


}
