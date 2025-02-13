package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.userservice.entity.UserStatsEntity;

public interface UserStatsRepository extends JpaRepository<UserStatsEntity, Long> {
}
