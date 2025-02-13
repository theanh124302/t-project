package tproject.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;

import java.sql.Timestamp;

@Entity
@Table(name = "user_blocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBlockEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "blocker_id")
    private TUserEntity blocker;

    @ManyToOne
    @JoinColumn(name = "blocked_id")
    private TUserEntity blocked;

    @Column(name = "block_date")
    private Timestamp blockDate;

}