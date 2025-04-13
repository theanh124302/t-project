package tproject.postservice.storage;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tproject.postservice.dto.response.S3PreSignedUrlDto;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class S3PreSignedUrlService {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.cloudfront.domain}")
    private String cloudFrontDomain;

    private static final String HTTPS_URL = "https://";

    private static final String SLASH = "/";

    public S3PreSignedUrlService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String generatePreSignedUrl(String fileName, String contentType, int expirationMinutes) {

        String fileKey = fileName + '-' + UUID.randomUUID().toString();

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + (long) expirationMinutes * 60 * 1000;
        expiration.setTime(expTimeMillis);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(
                Headers.CONTENT_TYPE,
                contentType
        );

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

//        String cloudFrontUrl = HTTPS_URL + cloudFrontDomain + SLASH + fileKey;

        return url.toString();
    }

}
