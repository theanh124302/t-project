package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.userservice.entity.UserProfileEntity;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
}
