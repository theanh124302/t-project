package tproject.tcommon.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.exception.base.BaseException;
import tproject.tcommon.response.restfulresponse.RestfulResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public RestfulResponse<String> handleBaseException(BaseException e) {
        log.error(e.getMessage());
        return RestfulResponse.error(e.getMessage(), ResponseStatus.ERROR);
    }

}
