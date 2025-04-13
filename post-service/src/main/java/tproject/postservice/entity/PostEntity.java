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
import tproject.postservice.enumerates.PostStatus;
import tproject.postservice.enumerates.Visibility;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity extends BaseEntity {

    private Long userId;

    private PostStatus status;

    private Boolean hidden;

    private String content;

    boolean commentable;

    boolean shareable;

    boolean likeable;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

}
