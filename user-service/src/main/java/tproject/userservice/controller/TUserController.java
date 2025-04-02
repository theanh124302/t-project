package tproject.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.response.tuser.UserInformationResponseDto;
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
