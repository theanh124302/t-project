package tproject.postservice.storage.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ImageHandleDto extends FileHandleDto {
    private Integer width;
    private Integer height;
    private String format;
    private Boolean hasAlpha;
    private String thumbnailKey;
    private CacheControl cacheControl;

    @Getter
    public enum CacheControl {
        PUBLIC("public, max-age=31536000"),
        PRIVATE("private, max-age=3600"),
        NO_CACHE("no-cache");

        private final String value;

        CacheControl(String value) {
            this.value = value;
        }

    }

    public ImageHandleDto(String fileName, String fileType) {
        super(fileName, fileType);
        this.cacheControl = CacheControl.PUBLIC; // Default caching for images
        this.keyFile = generateKeyFile(fileType, this.id);
        this.setContentType(determineContentType(fileName));
    }

    @Override
    protected String generateKeyFile(String prefixKeyName, String id) {
        if (id == null) return null;
        return String.join("/", "images", prefixKeyName, id);
    }

    private String determineContentType(String fileName) {
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.toLowerCase().endsWith(".png")) {
            return "image/png";
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.toLowerCase().endsWith(".webp")) {
            return "image/webp";
        } else {
            return "application/octet-stream";
        }
    }

    public String generateThumbnailKey() {
        thumbnailKey = String.join("/", "thumbnails", this.id + "_thumb");
        return thumbnailKey;
    }

    @Override
    public Map<String, Object> createPutObjectRequestParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("bucket", getBucket());
        params.put("key", getKeyFile());
        params.put("contentType", getContentType());
        params.put("metadata", getS3Metadata());
        params.put("cacheControl", cacheControl.getValue());

        // Thêm metadata đặc biệt cho ảnh
        if (width != null && height != null) {
            this.addMetadata("image-width", width.toString());
            this.addMetadata("image-height", height.toString());
        }
        if (format != null) {
            this.addMetadata("image-format", format);
        }

        return params;
    }

    @Override
    public Map<String, Object> createGetObjectRequestParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("bucket", getBucket());
        params.put("key", getKeyFile());
        return params;
    }

    public Map<String, Object> createPresignedUrlParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("bucket", getBucket());
        params.put("key", getKeyFile());
        params.put("expiration", getExpirationTime() != null ? getExpirationTime() : 60); // Default: 60 phút
        return params;
    }

}