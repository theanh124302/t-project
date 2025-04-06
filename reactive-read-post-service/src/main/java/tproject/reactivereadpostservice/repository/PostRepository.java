package tproject.reactivereadpostservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import tproject.reactivereadpostservice.entity.PostEntity;

public interface PostRepository extends R2dbcRepository<PostEntity, Long> {

}
