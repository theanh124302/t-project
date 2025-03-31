package tproject.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tproject.userservice.dto.response.follow.FollowUserDto;
import tproject.userservice.entity.FollowEntity;
import tproject.userservice.enumeration.FollowStatus;

@Repository
public interface FollowReadOnlyRepository extends JpaRepository<FollowEntity, Long> {

    @Query(" SELECT new tproject.userservice.dto.response.follow.FollowUserDto(u.username, u.firstName, u.lastName) " +
            " FROM FollowEntity f " +
            " JOIN TUserEntity u ON f.sourceUserId = u.id " +
            " WHERE f.targetUserId = :userId AND f.status = :status ")
    Page<FollowUserDto> findFollowersByUserIdAndStatus(Long userId, FollowStatus status, Pageable pageable);

    @Query(" SELECT new tproject.userservice.dto.response.follow.FollowUserDto(u.username, u.firstName, u.lastName) " +
            " FROM FollowEntity f " +
            " JOIN TUserEntity u ON f.targetUserId = u.id " +
            " WHERE f.sourceUserId = :userId AND f.status = :status ")
    Page<FollowUserDto> findFollowingsByUserIdAndStatus(Long userId, FollowStatus status, Pageable pageable);

}
