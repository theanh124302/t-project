package tproject.userservice.enumeration;

import tproject.tcommon.response.message.ResponseMessage;

public class UserResponseMessage extends ResponseMessage {

    public static final String USER_NOT_FOUND = "user.not.found";
    public static final String USERNAME_ALREADY_EXISTS = "username.already.exists";
    public static final String USER_CREATED = "user.created";
    public static final String CANNOT_FOLLOW_YOURSELF = "cannot.follow.yourself";
    public static final String USERNAME_CANNOT_BE_NULL = "username.cannot.be.null";
    public static final String USER_ID_CANNOT_BE_NULL = "user.id.cannot.be.null";
    public static final String FOLLOW_NOT_FOUND = "follow.not.found";
    public static final String USER_PRIVACY_NOT_FOUND = "user.privacy.not.found";

}
