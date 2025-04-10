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
import tproject.postservice.enumerates.MediaProcessStatus;
import tproject.postservice.enumerates.MediaType;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "medias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaEntity extends BaseEntity {

    private Long postId;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    private MediaProcessStatus status;

    private Boolean hidden;

}
