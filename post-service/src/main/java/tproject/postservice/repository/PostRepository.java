package tproject.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tproject.postservice.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
