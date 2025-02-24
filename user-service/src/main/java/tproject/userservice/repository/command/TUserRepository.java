package tproject.userservice.repository.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tproject.userservice.entity.TUserEntity;

import java.util.Optional;

@Repository
public interface TUserRepository extends JpaRepository<TUserEntity, Long> {
    Optional<TUserEntity> findByUsername(String username);
    Optional<TUserEntity> findByAuthId(Long authId);
}
