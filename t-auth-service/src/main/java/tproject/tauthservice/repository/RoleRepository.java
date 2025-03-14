package tproject.tauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.tauthservice.entity.RoleEntity;
import tproject.tauthservice.enumerates.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(Role name);
    List<RoleEntity> findAllByNameIn(List<Role> names);
}
