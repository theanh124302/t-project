package tproject.nonreactivewritepostservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;


@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity extends BaseEntity {

    private Long userId;
    private String title;
    private String content;
    private String imageUrl;
    private String videoUrl;
    private String audioUrl;
    private String postType;
    private String status;
    private String category;
    private String tags;
    private int likesCount;
    private int commentsCount;
    private int sharesCount;

}
