package tproject.userservice.repository.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.UserStatusEntity;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusEntity, Long> {
}
