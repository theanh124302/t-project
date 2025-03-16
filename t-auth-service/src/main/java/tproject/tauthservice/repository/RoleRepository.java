package tproject.tauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tproject.tauthservice.entity.RoleEntity;
import tproject.tauthservice.enumerates.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByNameIn(List<Role> names);

    @Query("SELECT r FROM RoleEntity r JOIN UserRoleEntity ur ON r.id = ur.roleId WHERE ur.userId = :userId")
    List<RoleEntity> findRolesByUserId(@Param("userId") Long userId);

}
