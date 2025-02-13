package tproject.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TUserEntity extends BaseEntity {

    private Long authId;

    @Column(unique = true)
    private String username;

    private String fullName;

    private String bio;

    private String avatarUrl;

}
