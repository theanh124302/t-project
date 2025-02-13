package tproject.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tcommon.enums.RestfulResponse.RestfulStatus;
import tproject.tcommon.response.RestfulResponse;

@RestController
@RequestMapping("/users")
public class InformationController {

    private static final Logger log = LoggerFactory.getLogger(InformationController.class);

    @GetMapping("/test")
    public RestfulResponse<String> test() {
        log.info("Path requested: /users/test");
        return RestfulResponse.<String>builder()
                .status(RestfulResponse.StatusMetadata.builder()
                        .code(200)
                        .message("OK")
                        .status(RestfulStatus.SUCCESS)
                        .build())
                .data(", world!")
                .build();
    }
}