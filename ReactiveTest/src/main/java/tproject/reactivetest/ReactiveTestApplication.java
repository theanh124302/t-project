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
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

import static java.lang.Thread.sleep;

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



//    @Bean
//    public RestClient restClient() {
//        return RestClient.builder()
//                .baseUrl("https://jsonplaceholder.typicode.com")
//                .build();
//    }

    @RestController
    static class ReactiveBenchmarkController {

        private final WebClient webClient;

        private final EngineerRepository engineerRepository;

        private static final String TARGET_ENDPOINT = "/posts";

        private static final int API_CALLS_PER_REQUEST = 5;

        public ReactiveBenchmarkController(WebClient webClient,
                                            EngineerRepository engineerRepository) {
            this.webClient = webClient;
            this.engineerRepository = engineerRepository;

        }

        private Long getRandomLong() throws InterruptedException {
            log.info("Thread run blocking call: {}", Thread.currentThread().getName());
            Random random = new Random();
            sleep(250);
            return random.nextLong();
        }


        @GetMapping("/api")
        public Mono<Long> handleApiRequest() throws InterruptedException {

            UUID requestId = UUID.randomUUID();

            Instant start = Instant.now();

            log.info("Thread before API calls: {} on requestId: {}", Thread.currentThread().getName(), requestId);
//
//            return Flux.range(0, API_CALLS_PER_REQUEST)
//                    .flatMap(i -> {
//                        log.info("Thread during API call {}: {} on requestId: {}", i, Thread.currentThread().getName(), requestId);
//
//                        String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();
//
//                        try {
//                            return webClient.get()
//                                    .uri(endpoint)
//                                    .retrieve()
//                                    .bodyToMono(Object.class);
//                        } catch (RuntimeException e) {
//                            throw new RuntimeException("Error during API call", e);
//                        }
//
//                    })
//                    .collectList()
//                    .map(results -> {
//
//                        log.info("Thread after API calls: {} on requestId: {}", Thread.currentThread().getName(), requestId);
//
////                        log.info("results: {}", results);
//
//                        Instant end = Instant.now();
//                        Duration duration = Duration.between(start, end);
//
//                        long processingTime = duration.toMillis();
//
//                        log.info("Total processing time: {} ms on requestId: {}", processingTime, requestId);
//
//                        return processingTime;
//                    });



            // Tạo danh sách các operations (3 API calls + 2 database queries)
            List<Mono<?>> operations = new ArrayList<>();

            // Thêm 3 API calls
            for (int i = 0; i < API_CALLS_PER_REQUEST; i++) {
                final int index = i;
                String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();

                Mono<Object> apiCall = webClient.get()
                        .uri(endpoint)
                        .retrieve()
                        .bodyToMono(Object.class)
                        .doOnSubscribe(s -> log.info("Starting API call {}: {} on requestId: {}",
                                index, Thread.currentThread().getName(), requestId))
                        .doOnNext(result -> log.info("Completed API call"));

//                Mono<Long> testBlocking = Mono.fromCallable(() -> {
//                            log.info("Executing blocking operation on thread: {}", Thread.currentThread().getName());
//                            return getRandomLong();
//                        })
//                        .subscribeOn(Schedulers.boundedElastic())
//                        .doOnSubscribe(s -> log.info("Starting blocking call {}: {} on requestId: {}",
//                                index, Thread.currentThread().getName(), requestId))
//                        .doOnNext(result -> log.info("Completed blocking call {}: {} on requestId: {}",
//                                index, Thread.currentThread().getName(), requestId));

                operations.add(apiCall);
            }

//            // Thêm 2 database queries
//            Mono<List<EngineerEntity>> dbQuery1 = engineerRepository.findAll()
//                    .collectList()
//                    .doOnSubscribe(s -> log.info("Starting DB query 1: {} on requestId: {}",
//                            Thread.currentThread().getName(), requestId))
//                    .doOnNext(result -> log.info("Completed DB query 1, found {} records", result.size()));
//
//            operations.add(dbQuery1);
//
//
//            Mono<EngineerEntity> dbQuery2 = engineerRepository.findById(1L)
//                    .doOnSubscribe(s -> log.info("Starting DB query 2: {} on requestId: {}",
//                            Thread.currentThread().getName(), requestId))
//                    .doOnNext(result -> log.info("Completed DB query 2 on thread: {}",
//                            Thread.currentThread().getName()));
//
//
//
//            operations.add(dbQuery2);

//            Mono<Long> testScheduler = Mono.fromCallable(() -> {
//                        log.info("Thread before test scheduler: {} on requestId: {}", Thread.currentThread().getName(), requestId);
//                        Thread.sleep(1000);
//                        return 2L;
//                    }).subscribeOn(Schedulers.boundedElastic())
//                    .doOnSubscribe(s -> log.info("Starting test scheduler: {} on requestId: {}",
//                            Thread.currentThread().getName(), requestId))
//                    .doOnNext(result -> log.info("Completed test scheduler on thread: {}",
//                            Thread.currentThread().getName()));

//            operations.add(testScheduler);

            // Thực hiện tất cả các operations song song
            return Flux.merge(operations)
                    .collectList()
                    .map(results -> {
                        log.info("Thread after all operations: {} on requestId: {}",
                                Thread.currentThread().getName(), requestId);

                        Instant end = Instant.now();
                        Duration duration = Duration.between(start, end);
                        long processingTime = duration.toMillis();

                        log.info("Total processing time: {} ms on requestId: {}", processingTime, requestId);
                        return processingTime;
                    });
        }
    }



}
