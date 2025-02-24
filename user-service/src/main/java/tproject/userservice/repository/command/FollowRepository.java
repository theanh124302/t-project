package tproject.userservice.repository.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.enumeration.FollowStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByFollowerUsernameAndFollowingUsername(String followerUsername, String followingUsername);
    List<FollowEntity> findByFollowerUsername(String followerUsername);
    List<FollowEntity> findByFollowingUsername(String followingUsername);
    List<FollowEntity> findByFollowerUsernameAndStatus(String followerUsername, FollowStatus status);
    List<FollowEntity> findByFollowingUsernameAndStatus(String followingUsername, FollowStatus status);
}
