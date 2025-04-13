package tproject.postservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class S3PreSignedUrlDto {

    private String preSignedUrl;

    private String cloudFrontUrl;

    private String fileKey;

}
