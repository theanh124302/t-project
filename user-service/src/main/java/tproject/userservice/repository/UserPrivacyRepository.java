package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.UserPrivacyEntity;

import java.util.Optional;

@Repository
public interface UserPrivacyRepository extends JpaRepository<UserPrivacyEntity, Long> {
    Optional<UserPrivacyEntity> findByUserId(Long userId);
}
