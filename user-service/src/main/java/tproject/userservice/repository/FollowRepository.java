package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.enumeration.FollowStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findBySourceUserIdAndTargetUserId(Long sourceUserId, Long targetUserId);
}
