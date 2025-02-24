package tproject.userservice.repository.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.UserStatsEntity;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStatsEntity, Long> {
}
