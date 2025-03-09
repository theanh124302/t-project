package tproject.tcommon.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.restfulresponse.RestfulResponse;

@RestController
public class TestCommonController {

    @GetMapping("users/test")
    public RestfulResponse<String> test() {
        throw new RuntimeException("Hello World");
    }
}
