package tproject.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.TUserEntity;

@Repository
public interface TUserRepository extends JpaRepository<TUserEntity, Long> {
}
