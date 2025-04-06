package tproject.reactivereadpostservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import tproject.reactivereadpostservice.entity.ContentEntity;

public interface ContentRepository extends R2dbcRepository<ContentEntity, Long> {
}
