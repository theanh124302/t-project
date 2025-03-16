package tproject.tauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.tauthservice.entity.AuthenticationEntity;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Long> {

    Optional<AuthenticationEntity> findByUsername(String username);
}