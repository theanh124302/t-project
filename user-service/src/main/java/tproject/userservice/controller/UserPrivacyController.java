package tproject.userservice.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tcommon.response.restfulresponse.RestfulResponse;
import tproject.userservice.dto.response.privacy.UserPrivacyResponseDto;

@RestController
@RequestMapping("/user-privacy/v1")
public class UserPrivacyController {

    @PutMapping("/change")
    public RestfulResponse<UserPrivacyResponseDto> changePrivacy() {
        return null;
    }

}
