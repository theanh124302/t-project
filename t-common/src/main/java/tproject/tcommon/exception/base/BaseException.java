package tproject.tcommon.exception.base;

import lombok.Getter;
import tproject.tcommon.enums.ResponseStatus;

@Getter
public class BaseException extends RuntimeException{
    private final ResponseStatus status;
    public BaseException(String message, ResponseStatus status) {
        super(message);
        this.status = status;
    }
}
