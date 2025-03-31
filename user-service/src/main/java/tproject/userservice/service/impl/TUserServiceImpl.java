package tproject.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.message.ResponseMessage;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.request.tuser.UserInformationRequestDto;
import tproject.userservice.dto.request.tuser.UserSearchRequestDto;
import tproject.userservice.dto.response.tuser.UserInformationResponseDto;
import tproject.userservice.entity.TUserEntity;
import tproject.userservice.repository.TUserRepository;
import tproject.userservice.service.TUserService;

@Service
@AllArgsConstructor
public class TUserServiceImpl implements TUserService {

    private static final Logger log = LoggerFactory.getLogger(TUserServiceImpl.class);

    private final TUserRepository tUserRepository;

    @Override
    public RestfulResponse<UserInformationResponseDto> getUserInformation(Long userId, Long actorId) {
        log.info("Get user information request: userId={}, actorId={}", userId, actorId);
        TUserEntity tUserEntity = tUserRepository.findById(userId).orElse(null);
        if (tUserEntity == null) {
            log.error("User not found with userId: {}", userId);
            return RestfulResponse.error(ResponseMessage.NOT_FOUND, ResponseStatus.ERROR);
        }
        UserInformationResponseDto userInformationResponseDto = UserInformationResponseDto.builder()
                .username(tUserEntity.getUsername())
                .lastName(tUserEntity.getLastName())
                .firstName(tUserEntity.getFirstName())
                .birthDate(tUserEntity.getBirthDate())
                .build();
        log.info("User information: {}", userInformationResponseDto);
        return RestfulResponse.success(userInformationResponseDto, ResponseStatus.SUCCESS);
    }

}
