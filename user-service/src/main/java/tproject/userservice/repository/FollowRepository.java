package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.FollowEntity;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
}
