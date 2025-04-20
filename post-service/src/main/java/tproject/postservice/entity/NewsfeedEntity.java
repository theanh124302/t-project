package tproject.postservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "newsfeed")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsfeedEntity extends BaseEntity {

    private Long userId;

    private Long postId;

    private String content;

    private String fileType;

    private String fileUrl;

    private String visibility;

    private String viewed;

    private int syncStatus;

}
