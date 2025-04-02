package tproject.userservice.exception;

import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.exception.base.BaseException;

public class FollowException extends BaseException {
    public FollowException(String message) {
        super(message, ResponseStatus.ERROR);
    }
}