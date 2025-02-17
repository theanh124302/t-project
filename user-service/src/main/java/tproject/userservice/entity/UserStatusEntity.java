package tproject.userservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;
import tproject.userservice.enumeration.UserStatusEnum;

import java.sql.Timestamp;

@Entity
@Table(name = "user_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusEntity extends BaseEntity {

    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @Column(name = "last_active")
    private Timestamp lastActive;

}