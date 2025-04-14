package tproject.postservice.storage.dto;

import tproject.postservice.enumerates.FileType;

public class FileDtoFactory {

    private FileDtoFactory() {
    }

    public static FileHandleDto createFileHandleDto(String fileName, FileType fileType) {
        if (fileType.equals(FileType.IMAGE)) {
            return new ImageHandleDto(fileName, fileType.toString());
        } else if (fileType.equals(FileType.VIDEO)) {
            return new VideoHandleDto(fileName, fileType.toString());
        } else {
            throw new IllegalArgumentException("Unknown file type: " + fileType);
        }
    }

}
