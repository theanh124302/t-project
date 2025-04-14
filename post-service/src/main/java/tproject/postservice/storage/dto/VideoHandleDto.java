package tproject.postservice.storage.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VideoHandleDto extends FileHandleDto {
    private Double duration;
    private String codec;
    private Integer bitrate;
    private Float frameRate;
    private String audioCodec;
    private String thumbnailKey;
    private List<String> streamingKeys; // HLS/DASH streaming
    private Boolean generateStreaming; // Cần transcode không
    private Map<String, String> streamingFormats; // Các format cần transcode

    public VideoHandleDto(String fileName, String fileType) {
        super(fileName, fileType);
        this.keyFile = generateKeyFile(fileType, this.id);
        this.setContentType(determineContentType(fileName));
        this.streamingKeys = new ArrayList<>();
        this.streamingFormats = new HashMap<>();
        this.generateStreaming = false;
    }

    @Override
    protected String generateKeyFile(String prefixKeyName, String id) {
        if (id == null) return null;
        return String.join("/", "videos", prefixKeyName, id);
    }

    private String determineContentType(String fileName) {
        if (fileName.toLowerCase().endsWith(".mp4")) {
            return "video/mp4";
        } else if (fileName.toLowerCase().endsWith(".mov")) {
            return "video/quicktime";
        } else if (fileName.toLowerCase().endsWith(".avi")) {
            return "video/x-msvideo";
        } else if (fileName.toLowerCase().endsWith(".webm")) {
            return "video/webm";
        } else {
            return "application/octet-stream";
        }
    }

    public String generateThumbnailKey() {
        thumbnailKey = String.join("/", "video-thumbnails", this.id + "_thumb");
        return thumbnailKey;
    }

    public void addStreamingFormat(String quality, String format) {
        this.streamingFormats.put(quality, format);
        this.generateStreaming = true;
    }

    public String generateStreamingKey(String quality) {
        String streamingKey = String.join("/", "streaming", quality, this.id, "index.m3u8");
        this.streamingKeys.add(streamingKey);
        return streamingKey;
    }

    @Override
    public Map<String, Object> createPutObjectRequestParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("bucket", getBucket());
        params.put("key", getKeyFile());
        params.put("contentType", getContentType());
        params.put("metadata", getS3Metadata());

        // Thêm metadata đặc biệt cho video
        if (duration != null) {
            this.addMetadata("video-duration", duration.toString());
        }
        if (codec != null) {
            this.addMetadata("video-codec", codec);
        }
        if (bitrate != null) {
            this.addMetadata("video-bitrate", bitrate.toString());
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

    // Tham số cho MediaConvert (AWS transcoding service)
    public Map<String, Object> createTranscodeParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("bucket", getBucket());
        params.put("key", getKeyFile());
        params.put("outputPath", String.join("/", "streaming", this.id));
        params.put("formats", streamingFormats);
        return params;
    }

    // Tạo CloudFront distribution config cho streaming
    public Map<String, Object> createDistributionParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("originPath", String.join("/", "/streaming", this.id));
        params.put("defaultRootObject", "index.m3u8");
        params.put("enableCaching", true);
        return params;
    }
}