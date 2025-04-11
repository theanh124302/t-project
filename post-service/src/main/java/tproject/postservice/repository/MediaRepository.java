package tproject.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.postservice.entity.MediaEntity;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
}
