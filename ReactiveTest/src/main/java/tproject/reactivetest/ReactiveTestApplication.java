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

        private static final String TARGET_ENDPOINT = "/posts";

        private static final int API_CALLS_PER_REQUEST = 5;

        public ReactiveBenchmarkController(WebClient webClient) {
            this.webClient = webClient;
        }

        @GetMapping("/api")
        public Mono<Long> handleApiRequest() {
            Instant start = Instant.now();

            log.info("Thread before API calls: {}", Thread.currentThread().getName());

            return Flux.range(0, API_CALLS_PER_REQUEST)
                    .flatMap(i -> {
                        log.info("Thread during API call {}: {}", i, Thread.currentThread().getName());

                        String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();

                        return webClient.get()
                                .uri(endpoint)
                                .retrieve()
                                .bodyToMono(Object.class);
                    })
                    .collectList()
                    .map(results -> {


                        log.info("Thread after API calls: {}", Thread.currentThread().getName());

                        log.info("results: {}", results);

                        Instant end = Instant.now();
                        Duration duration = Duration.between(start, end);

                        long processingTime = duration.toMillis();

                        log.info("Total processing time: {} ms", processingTime);

                        Map<String, Object> response = new HashMap<>();

                        return processingTime;
                    });
        }
    }
}
