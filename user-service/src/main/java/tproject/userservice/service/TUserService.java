package tproject.userservice.service;

import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.response.tuser.UserInformationResponseDto;

public interface TUserService {

    RestfulResponse<UserInformationResponseDto> getUserInformation(Long userId, Long actorId);

}
