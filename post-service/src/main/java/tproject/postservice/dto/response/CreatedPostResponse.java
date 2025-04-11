package tproject.postservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatedPostResponse {

    private Long postId;
    private String content;
    private String mediaUrl;

}
