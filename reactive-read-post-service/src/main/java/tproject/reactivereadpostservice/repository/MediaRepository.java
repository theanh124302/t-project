package tproject.reactivereadpostservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import tproject.reactivereadpostservice.entity.MediaEntity;

public interface MediaRepository extends R2dbcRepository<MediaEntity, Long> {
}
