package tproject.authservice.entity;

import jakarta.persistence.*;
import lombok.*;
import tproject.tcommon.model.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    private Long authId;

    @NonNull
    @Column(unique = true)
    private String username;

    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String bio;

    private String avatarUrl;

}

