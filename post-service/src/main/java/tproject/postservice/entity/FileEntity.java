package tproject.postservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.postservice.enumerates.FileProcessStatus;
import tproject.postservice.enumerates.FileType;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity extends BaseEntity {

    private Long postId;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private String fileUrl;

    private String preSignedUrl;

    @Enumerated(EnumType.STRING)
    private FileProcessStatus status;

    private Boolean hidden;

}
