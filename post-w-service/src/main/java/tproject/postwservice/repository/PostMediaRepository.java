package tproject.postwservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import tproject.postwservice.entity.PostMediaEntity;

public interface PostMediaRepository extends R2dbcRepository<PostMediaEntity, Long> {
}
