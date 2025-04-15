package tproject.postservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatedLightPostResponseDTO {

    private Long postId;

    private String status;

    private String fileUrl;

}
