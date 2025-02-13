package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.userservice.entity.UserFollowerEntity;

public interface UserFollowerRepository extends JpaRepository<UserFollowerEntity, Long> {
}
