package tproject.postwservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import tproject.postwservice.entity.PostEntity;

public interface PostRepository extends R2dbcRepository<PostEntity, Long> {

}
