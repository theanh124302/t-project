package tproject.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.postservice.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
