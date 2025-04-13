package tproject.postservice.util.media;

public class ImageHandle implements MediaHandle {
    @Override
    public String generateMediaUrl() {
        return "https://example.com/image.jpg";
    }
}
