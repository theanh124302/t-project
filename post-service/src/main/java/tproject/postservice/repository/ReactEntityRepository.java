package tproject.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.postservice.entity.ReactEntity;

public interface ReactEntityRepository extends JpaRepository<ReactEntity, Long> {
}
