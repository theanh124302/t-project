package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.userservice.entity.UserStatusEntity;

public interface UserStatusRepository extends JpaRepository<UserStatusEntity, Long> {
}
