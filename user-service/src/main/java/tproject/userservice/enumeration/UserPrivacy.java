package tproject.userservice.enumeration;

import lombok.Getter;

@Getter
public enum UserPrivacy {

    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE");

    private final String privacy;

    UserPrivacy(String privacy) {
        this.privacy = privacy;
    }

}
