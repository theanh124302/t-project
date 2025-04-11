package tproject.postservice.util.media;

public class PdfHandle implements MediaHandle {
    @Override
    public String generateMediaUrl() {
        return "https://example.com/sample.pdf";
    }
}
