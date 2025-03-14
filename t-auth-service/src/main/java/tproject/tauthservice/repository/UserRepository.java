package tproject.tauthservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tproject.tauthservice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}