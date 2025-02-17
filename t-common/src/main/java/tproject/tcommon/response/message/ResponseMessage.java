package tproject.tcommon.response.message;

public final class ResponseMessage {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String NOT_FOUND = "not.found";

    private ResponseMessage() {
        throw new IllegalStateException("Utility class");
    }

}
