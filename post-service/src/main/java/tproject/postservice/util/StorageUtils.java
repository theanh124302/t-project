package tproject.postservice.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class StorageUtils {


    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.cloudfront.domain}")
    private String cloudFrontDomain;

    AmazonS3 s3Client;

    public StorageUtils(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String getFileType(String fileName) {
        String fileType = null;
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
            fileType = "image";
        } else if (fileName.endsWith(".mp4") || fileName.endsWith(".avi")) {
            fileType = "video";
        } else if (fileName.endsWith(".pdf")) {
            fileType = "pdf";
        }
        return fileType;
    }


    public String generateContentType(String fileName) {
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.toLowerCase().endsWith(".png")) {
            return "image/png";
        } else if (fileName.toLowerCase().endsWith(".mp4")) {
            return "video/mp4";
        } else if (fileName.toLowerCase().endsWith(".avi")) {
            return "video/x-msvideo";
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            return "application/pdf";
        } else {
            return "application/octet-stream";
        }
    }

    public String generateKeyFile(String fileName) {
        return String.join("/", generateContentType(fileName), UUID.randomUUID().toString());
    }


    public String genPreSignedUrl(String fileName) {
        String keyFile = generateKeyFile(fileName);
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + 3 * 60 * 1000;
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(this.bucketName, keyFile)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        String contentType = generateContentType(fileName);
        generatePresignedUrlRequest.addRequestParameter(
                Headers.CONTENT_TYPE,
                contentType
        );
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public String uploadFile(String fileName, File file) {
        if(fileName == null){
            fileName = "null";
        }
        String keyFile = generateKeyFile(fileName);
        PutObjectRequest uploadRequest = new PutObjectRequest(this.bucketName, keyFile, file);
        s3Client.putObject(uploadRequest);
        return this.cloudFrontDomain + "/" + s3Client.getUrl(this.bucketName, keyFile).toString();
    }

}
