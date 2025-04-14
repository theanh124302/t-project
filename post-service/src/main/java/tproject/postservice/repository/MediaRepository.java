package tproject.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.postservice.entity.FileEntity;

public interface MediaRepository extends JpaRepository<FileEntity, Long> {
}
