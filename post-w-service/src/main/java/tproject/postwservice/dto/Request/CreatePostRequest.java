package tproject.postwservice.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.postwservice.enumeration.MediaType;
import tproject.postwservice.enumeration.Visibility;

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

    private PostContent postContent;

    private PostMedia postMedia;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostContent {
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostMedia {
        private String mediaUrl;
        private MediaType mediaType;
    }

}
