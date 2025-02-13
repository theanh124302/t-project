package tproject.tcommon.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tcommon.enums.RestfulResponse.RestfulStatus;
import tproject.tcommon.response.RestfulResponse;

@RestController
public class TestCommonController {

    @GetMapping("users/test")
    public RestfulResponse<String> test() {
        return RestfulResponse.<String>builder()
                .status(RestfulResponse.StatusMetadata.builder()
                        .code(200)
                        .message("OK")
                        .status(RestfulStatus.SUCCESS)
                        .build())
                .data("Hello, world!")
                .build();
    }
}
