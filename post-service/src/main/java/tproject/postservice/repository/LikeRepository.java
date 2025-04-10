package tproject.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.postservice.entity.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
}
