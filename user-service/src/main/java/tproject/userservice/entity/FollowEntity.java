package tproject.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;
import tproject.userservice.enumeration.FollowStatus;

import java.sql.Timestamp;

@Entity
@Table(name = "user_followers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowEntity extends BaseEntity {

    @NonNull
    private Long followerId;

    @NonNull
    private Long followingId;

    @NonNull
    @Enumerated(EnumType.STRING)
    private FollowStatus status;

    private Timestamp followDate;

}