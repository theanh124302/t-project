package tproject.reactivetest;


import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface EngineerRepository extends R2dbcRepository<EngineerEntity, Long> {

}
