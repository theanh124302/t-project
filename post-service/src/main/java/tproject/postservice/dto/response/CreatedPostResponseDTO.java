package tproject.postservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatedPostResponseDTO {

    private Long postId;

    private String content;

    private String preSignedUrl;

    private String mediaUrl;

}
