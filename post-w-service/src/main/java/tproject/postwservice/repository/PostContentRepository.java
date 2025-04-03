package tproject.postwservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import tproject.postwservice.entity.PostContentEntity;

public interface PostContentRepository extends R2dbcRepository<PostContentEntity, Long> {
}
