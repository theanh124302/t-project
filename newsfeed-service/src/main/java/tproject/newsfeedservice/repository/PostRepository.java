package tproject.newsfeedservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import tproject.newsfeedservice.entity.PostEntity;

public interface PostRepository extends R2dbcRepository<PostEntity, Integer> {
}
