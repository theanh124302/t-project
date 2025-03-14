package tproject.tauthservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tproject.tauthservice.entity.AuthenticationEntity;
import tproject.tauthservice.entity.RoleEntity;
import tproject.tauthservice.entity.UserEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationDto implements UserDetails {

    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Set<RoleEntity> roles = new HashSet<>();

    public AuthenticationDto(AuthenticationDto authenticationDto) {
        this.userId = authenticationDto.getUserId();
        this.username = authenticationDto.getUsername();
        this.password = authenticationDto.getPassword();
        this.roles = authenticationDto.getRoles();
    }

    public AuthenticationDto(AuthenticationEntity authenticationEntity, Set<RoleEntity> roles) {
        this.id = authenticationEntity.getId();
        this.userId = authenticationEntity.getUserId();
        this.username = authenticationEntity.getUsername();
        this.password = authenticationEntity.getPassword();
        this.roles = roles;
    }

    public AuthenticationDto(AuthenticationEntity authenticationEntity) {
        this.userId = authenticationEntity.getUserId();
        this.username = authenticationEntity.getUsername();
        this.password = authenticationEntity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
