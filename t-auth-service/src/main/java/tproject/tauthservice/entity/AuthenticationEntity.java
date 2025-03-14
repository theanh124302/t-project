package tproject.tauthservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.tcommon.model.BaseEntity;

@Entity
@Table(name = "authentications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationEntity extends BaseEntity {

    private Long userId;
    private String username;
    private String password;

}
