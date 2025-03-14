package tproject.tauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tproject.tauthservice.dto.AuthenticationDto;
import tproject.tauthservice.entity.AuthenticationEntity;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Long> {

    @Query(value = "SELECT new tproject.tauthservice.dto.AuthenticationDto(a, " +
            "(SELECT r FROM RoleEntity r JOIN UserRoleEntity ur ON r.id = ur.roleId WHERE ur.userId = a.userId)) " +
            "FROM AuthenticationEntity a WHERE a.username = :username")
    Optional<AuthenticationDto> findAuthenticationDtoByUsername(@Param("username") String username);

    @Query(value = "SELECT new tproject.tauthservice.dto.AuthenticationDto(a, " +
            "(SELECT r FROM RoleEntity r JOIN UserRoleEntity ur ON r.id = ur.roleId WHERE ur.userId = a.userId)) " +
            "FROM AuthenticationEntity a WHERE a.userId = :userId")
    Optional<AuthenticationDto> findAuthenticationDtoByUserId(@Param("userId") Long userId);

    Optional<AuthenticationEntity> findByUsername(String username);
}