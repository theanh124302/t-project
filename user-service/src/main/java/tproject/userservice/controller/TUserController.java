package tproject.userservice.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.request.tuser.UserInformationRequestDto;
import tproject.userservice.dto.request.tuser.UserRegisterRequestDto;
import tproject.userservice.dto.request.tuser.UserUpdateRequestDto;
import tproject.userservice.dto.response.tuser.UserInformationResponseDto;
import tproject.userservice.dto.response.tuser.UserRegisterResponseDto;
import tproject.userservice.dto.response.tuser.UserUpdateResponseDto;
import tproject.userservice.service.TUserService;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class TUserController {

    private final TUserService userService;

    @GetMapping("/{userId}/info")
    public RestfulResponse<UserInformationResponseDto> getUserInformation(@PathVariable Long userId, @AuthenticationPrincipal Jwt jwt) {
        return userService.getUserInformation(userId, Long.parseLong(jwt.getSubject()));
    }

}
