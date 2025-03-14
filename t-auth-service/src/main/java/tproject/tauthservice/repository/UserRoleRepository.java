package tproject.tauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.tauthservice.entity.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
