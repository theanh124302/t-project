package tproject.postservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatedMassivePostResponseDTO {

    private Long postId;

    private String status;

    private String preSignedUrl;

}

