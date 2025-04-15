package tproject.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.postservice.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
