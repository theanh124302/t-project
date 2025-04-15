package tproject.postservice.storage;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.UploadObjectRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
@AllArgsConstructor
public class StorageUtils {

    AmazonS3 s3Client;

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

    public String generateKeyFile(String prefixKeyName) {
        return String.join("/", prefixKeyName, UUID.randomUUID().toString());
    }


    public String genPreSignedUrl(String prefixKeyName, String bucketName, String fileName) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + 3 * 60 * 1000;
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, generateKeyFile(prefixKeyName))
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

    public String uploadPublicFile(String bucketName, String keyName, File file) {
        PutObjectRequest uploadRequest = new PutObjectRequest(bucketName, keyName, file)
                .withCannedAcl(com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead);
        s3Client.putObject(uploadRequest);
        return s3Client.getUrl(bucketName, keyName).toString();
    }

}
