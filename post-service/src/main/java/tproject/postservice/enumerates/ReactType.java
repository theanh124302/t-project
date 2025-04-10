package tproject.postservice.enumerates;

public enum ReactType {
    LIKE("LIKE"),
    LOVE("LOVE"),
    HAHA("HAHA"),
    WOW("WOW"),
    SAD("SAD"),
    ANGRY("ANGRY");

    private final String value;

    ReactType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
