package tproject.postservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.postservice.enumerates.MediaType;
import tproject.postservice.enumerates.Visibility;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    private String status;

    private Visibility visibility;

    private Instant createdAt;

    private String content;

    private Media postMedia;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Media {
        private String mediaUrl;
        private MediaType mediaType;
    }

}

