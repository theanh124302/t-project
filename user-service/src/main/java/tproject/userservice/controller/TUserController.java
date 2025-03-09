package tproject.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.request.tuser.UserInformationRequestDto;
import tproject.userservice.dto.request.tuser.UserRegisterRequestDto;
import tproject.userservice.dto.request.tuser.UserUpdateRequestDto;
import tproject.userservice.dto.response.tuser.UserInformationResponseDto;
import tproject.userservice.dto.response.tuser.UserRegisterResponseDto;
import tproject.userservice.dto.response.tuser.UserUpdateResponseDto;
import tproject.userservice.service.TUserService;

@RestController
@RequestMapping("/user")
public class TUserController {

    private static final Logger log = LoggerFactory.getLogger(TUserController.class);
    private final TUserService userService;

    public TUserController(TUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public RestfulResponse<UserInformationResponseDto> getUserInformation(@RequestBody @Validated UserInformationRequestDto userInformationRequestDto) {
        return userService.getUserInformation(userInformationRequestDto);
    }

    @PostMapping("/register")
    public RestfulResponse<UserRegisterResponseDto> registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        return userService.registerUser(userRegisterRequestDto);
    }

    @PostMapping("/update")
    public RestfulResponse<UserUpdateResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.updateUser(userUpdateRequestDto);
    }

}
