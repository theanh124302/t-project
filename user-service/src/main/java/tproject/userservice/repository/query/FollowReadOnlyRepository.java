package tproject.userservice.repository.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tproject.userservice.dto.response.follow.FollowUserDto;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.enumeration.FollowStatus;

import java.util.List;

@Repository
public interface FollowReadOnlyRepository extends JpaRepository<FollowEntity, Long> {

    @Query(" SELECT new tproject.userservice.dto.response.follow.FollowUserDto(u.username, u.firstName, u.lastName, u.avatarUrl) " +
            " FROM FollowEntity f " +
            " JOIN TUserEntity u ON f.followerUsername = u.username " +
            " WHERE f.followingUsername = :username AND f.status = :status ")
    List<FollowUserDto> findFollowersByUserIdAndStatus(String username, FollowStatus status);

    @Query(" SELECT new tproject.userservice.dto.response.follow.FollowUserDto(u.username, u.firstName, u.lastName, u.avatarUrl) " +
            " FROM FollowEntity f " +
            " JOIN TUserEntity u ON f.followingUsername = u.username " +
            " WHERE f.followerUsername = :username AND f.status = :status ")
    List<FollowUserDto> findFollowingsByUserIdAndStatus(String username, FollowStatus status);

}
