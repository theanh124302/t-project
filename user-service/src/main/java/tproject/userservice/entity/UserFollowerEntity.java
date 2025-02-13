package tproject.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;

import java.sql.Timestamp;

@Entity
@Table(name = "user_followers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowerEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private TUserEntity follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private TUserEntity following;

    @Column(name = "follow_date")
    private Timestamp followDate;

}