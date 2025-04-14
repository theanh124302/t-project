package tproject.postservice.storage.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public abstract class FileHandleDto {
    protected final String id;
    private final String fileName;
    private final String fileType;
    protected String keyFile;
    private String bucket;
    private String contentType;
    private Map<String, String> metadata;
    private Long contentLength;
    private String eTag;
    private String url;
    private Integer expirationTime; // Thời gian hết hạn cho presigned URL (phút)

    FileHandleDto(String fileName, String fileType) {
        this.id = UUID.randomUUID().toString();
        this.fileName = fileName;
        this.fileType = fileType;
        this.metadata = new HashMap<>();
    }

    protected abstract String generateKeyFile(String prefixKeyName, String id);

    // Phương thức chung cho S3
    public Map<String, String> getS3Metadata() {
        return metadata;
    }

    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }

    // Tạo request cho upload
    public abstract Map<String, Object> createPutObjectRequestParams();

    // Tạo request cho download
    public abstract Map<String, Object> createGetObjectRequestParams();
}