package tproject.tcommon.response.restfulresponse;

import lombok.*;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.message.ResponseMessage;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestfulResponse<T> {
    private Metadata metadata;
    private T data;
    private PageMetadata page;

    public static <T> RestfulResponse<T> success(T data, ResponseStatus status) {
        return RestfulResponse.<T>builder()
                .metadata(RestfulResponse.Metadata.builder()
                        .message(ResponseMessage.SUCCESS)
                        .status(status)
                        .build())
                .data(data)
                .build();
    }

    public static <T> RestfulResponse<T> success(T data, PageMetadata page, ResponseStatus status) {
        return RestfulResponse.<T>builder()
                .metadata(RestfulResponse.Metadata.builder()
                        .message(ResponseMessage.SUCCESS)
                        .status(status)
                        .build())
                .data(data)
                .page(page)
                .build();
    }

    public static <T> RestfulResponse<T> error(String message, ResponseStatus status) {
        return RestfulResponse.<T>builder()
                .metadata(RestfulResponse.Metadata.builder()
                        .message(message)
                        .status(status)
                        .build())
                .build();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Metadata {
        private String message;
        private ResponseStatus status;
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