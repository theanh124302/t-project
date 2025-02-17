package tproject.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "user_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsEntity extends BaseEntity {

    private Long userId;

    private Integer likeCount;

    private Integer commentCount;

    private Integer shareCount;

    private Integer likeReceivedCount;

    private Integer commentReceivedCount;

    private Integer shareReceivedCount;

    private Integer postCount;

    private Integer followerCount;

    private Integer followingCount;

}
