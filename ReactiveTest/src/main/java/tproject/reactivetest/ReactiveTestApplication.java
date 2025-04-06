package tproject.reactivetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class ReactiveTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveTestApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(ReactiveTestApplication.class);

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    @RestController
    static class ReactiveBenchmarkController {

        private final WebClient webClient;

        private static final String TARGET_ENDPOINT = "/posts";

        private static final int API_CALLS_PER_REQUEST = 5;

        public ReactiveBenchmarkController(WebClient webClient) {
            this.webClient = webClient;
        }

        @GetMapping("/api")
        public Mono<Long> handleApiRequest() {

            UUID requestId = UUID.randomUUID();

            Instant start = Instant.now();

            log.info("Thread before API calls: {} on requestId: {}", Thread.currentThread().getName(), requestId);

            return Flux.range(0, API_CALLS_PER_REQUEST)
                    .flatMap(i -> {
                        log.info("Thread during API call {}: {} on requestId: {}", i, Thread.currentThread().getName(), requestId);

                        String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();

                        try {
                            return webClient.get()
                                    .uri(endpoint)
                                    .retrieve()
                                    .bodyToMono(Object.class);
                        } catch (RuntimeException e) {
                            throw new RuntimeException("Error during API call", e);
                        }

                    })
                    .collectList()
                    .map(results -> {

                        log.info("Thread after API calls: {} on requestId: {}", Thread.currentThread().getName(), requestId);

//                        log.info("results: {}", results);

                        Instant end = Instant.now();
                        Duration duration = Duration.between(start, end);

                        long processingTime = duration.toMillis();

                        log.info("Total processing time: {} ms on requestId: {}", processingTime, requestId);

                        return processingTime;
                    });
        }
    }

    @Table("posts")
    public class EngineerEntity {

        @Id
        private Long id;

        private Long countryId;

    }

    public interface EngineerRepository extends R2dbcRepository<EngineerEntity, Long> {

    }


}
