package tproject.tauthservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private LocalDate birthDate;

}