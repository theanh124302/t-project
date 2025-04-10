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
import tproject.postservice.enumerates.ReactType;
import tproject.postservice.enumerates.ReactableItem;
import tproject.postservice.enumerates.Visibility;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "reacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactEntity extends BaseEntity {

    private Long postId;

    private Long userId;

    private String status;

    @Enumerated(EnumType.STRING)
    private ReactType type;

    @Enumerated(EnumType.STRING)
    private Visibility likeVisibility;

    @Enumerated(EnumType.STRING)
    private ReactableItem reactableItem;

}
