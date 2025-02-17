package tproject.tcommon.enums;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    SUCCESS("success"),
    ERROR("error"),
    WARNING("warning");

    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }

}
