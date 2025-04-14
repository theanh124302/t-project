package tproject.postservice.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileHandle {

    String generatePreSignedUrl();

    String generateFileUrl(String fileName);

    String uploadFile(MultipartFile file);

    String getFileName(String fileUrl);

    String getFilePath(String fileUrl);

    String getFileType(String fileUrl);

    String getFileSize(String fileUrl);

    String deleteFile(String fileUrl);

}
