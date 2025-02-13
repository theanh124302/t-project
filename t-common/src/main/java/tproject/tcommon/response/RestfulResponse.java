package tproject.tcommon.response;

import lombok.*;
import tproject.tcommon.enums.RestfulResponse.RestfulStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestfulResponse<T> {
    private StatusMetadata status;
    private T data;
    private PageMetadata page;
    private String timestamp;

    @Builder
    public RestfulResponse(StatusMetadata status, T data, PageMetadata page) {
        this.status = status;
        this.data = data;
        this.page = page;
        this.timestamp = LocalDateTime.now().toString();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StatusMetadata {
        private int code;
        private String message;
        private RestfulStatus status;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PageMetadata {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }

}
