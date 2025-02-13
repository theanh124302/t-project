package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.userservice.entity.UserBlockEntity;

public interface UserBlockRepository extends JpaRepository<UserBlockEntity, Long> {
}
