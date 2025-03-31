package tproject.userservice.exception;

import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.exception.base.BaseException;

public class UserPrivacyNotFoundException extends BaseException {
    public UserPrivacyNotFoundException(String message) {
        super(message, ResponseStatus.ERROR);
    }
}