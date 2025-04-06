package tproject.reactivetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

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

        // Target endpoint from JSONPlaceholder
        private static final String TARGET_ENDPOINT = "/posts";

        // Number of external API calls to make per client request
        private static final int API_CALLS_PER_REQUEST = 5;

        public ReactiveBenchmarkController(WebClient webClient) {
            this.webClient = webClient;
        }

        /**
         * Simple benchmark endpoint that makes multiple API calls
         * for each client request using reactive approach
         */
        @GetMapping("/api")
        public Mono<Map<String, Object>> handleApiRequest() {
            Instant start = Instant.now();

            log.info("Thread before API calls: {}", Thread.currentThread().getName());

            // Create a Flux that will make the API calls sequentially
            return Flux.range(0, API_CALLS_PER_REQUEST)
                    .flatMap(i -> {
                        log.info("Thread during API call {}: {}", i, Thread.currentThread().getName());

                        // Add a query parameter to avoid caching
                        String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();

                        Instant requestStart = Instant.now();

                        return webClient.get()
                                .uri(endpoint)
                                .retrieve()
                                .bodyToMono(Object.class)
                                .doOnNext(response -> {
                                    Instant responseTime = Instant.now();
                                    log.info("api {} call took {} ms", i,
                                            Duration.between(requestStart, responseTime).toMillis());
                                    log.info("Thread after API call {}: {}", i, Thread.currentThread().getName());
                                });
                    })
                    .collectList()
                    .map(results -> {
                        log.info("Thread after API calls: {}", Thread.currentThread().getName());

                        Instant end = Instant.now();
                        Duration duration = Duration.between(start, end);

                        log.info("Total processing time: {} ms", duration.toMillis());

                        Map<String, Object> response = new HashMap<>();
                        response.put("serverProcessingTimeMs", duration.toMillis());
                        response.put("externalApiCallsMade", API_CALLS_PER_REQUEST);

                        return response;
                    });
        }
    }
}
