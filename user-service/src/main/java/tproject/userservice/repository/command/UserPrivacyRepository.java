package tproject.userservice.repository.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.UserPrivacyEntity;

@Repository
public interface UserPrivacyRepository extends JpaRepository<UserPrivacyEntity, Long> {
}
