package tproject.tauthservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tauthservice.enumerates.Role;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role name;

    @Override
    public String toString() {
        return "RoleEntity{" +
                "name=" + name +
                '}';
    }
}