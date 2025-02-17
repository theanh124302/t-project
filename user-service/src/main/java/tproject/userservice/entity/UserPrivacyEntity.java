package tproject.userservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;
import tproject.userservice.enumeration.UserPrivacy;

@Entity
@Table(name = "user_privacy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrivacyEntity extends BaseEntity {

    private Long userId;

    @Column(name = "privacy_setting")
    @Enumerated(EnumType.STRING)
    private UserPrivacy privacy;

}
