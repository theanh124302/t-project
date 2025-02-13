package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.userservice.entity.UserSettingEntity;

public interface UserSettingRepository extends JpaRepository<UserSettingEntity, Long> {
}
