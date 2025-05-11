package tproject.reactivetest;


import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface EngineerRepository extends R2dbcRepository<EngineerEntity, Long> {
    Flux<EngineerEntity> findByTitle(String title);
}
