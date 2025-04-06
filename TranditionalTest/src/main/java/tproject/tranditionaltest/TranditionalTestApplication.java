package tproject.tranditionaltest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class TranditionalTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranditionalTestApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(TranditionalTestApplication.class);

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    @Bean
    public ExecutorService traditionalExecutorService() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int threadPoolSize = availableProcessors * 10;
        log.info("Initializing traditional thread pool with {} threads (based on {} processors)",
                threadPoolSize, availableProcessors);
        return Executors.newFixedThreadPool(threadPoolSize);
    }

//    @Bean
//    public ExecutorService virtualThreadExecutor() {
//        log.info("Initializing virtual thread executor");
//        return Executors.newVirtualThreadPerTaskExecutor();
//    }


    @RestController
    static
    class TraditionalThreadsBenchmarkController {

        private final RestClient restClient;

        private final ExecutorService executorService;

        private static final String TARGET_ENDPOINT = "/posts";

        private static final int API_CALLS_PER_REQUEST = 5;

        public TraditionalThreadsBenchmarkController(RestClient restClient
                , ExecutorService traditionalExecutorService) {
            this.restClient = restClient;
            this.executorService = traditionalExecutorService;
        }

        /**
         * Simple benchmark endpoint that makes multiple API calls
         * for each client request using traditional threads
         */
        @GetMapping("/api")
        public long handleApiRequest() {

            log.info("Thread before API calls: {}", Thread.currentThread().getName());

            Instant start = Instant.now();

            List<CompletableFuture<Object>> results = new ArrayList<>();

            for (int i = 0; i < API_CALLS_PER_REQUEST; i++) {

                String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();

                CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
					log.info("Async thread call API call: {}", Thread.currentThread().getName());
					return restClient.get()
							.uri(endpoint)
							.retrieve()
							.body(Object.class);
				}, executorService);

//                CompletableFuture<List<PostDto>> future = CompletableFuture.supplyAsync(() -> {
//                    try {
//                        log.info("Virtual thread call API: {}", Thread.currentThread().getName());
//                        PostDto[] posts = restClient.get()
//                                .uri(endpoint)
//                                .retrieve()
//                                .body(PostDto[].class);
//                        return Arrays.asList(posts);
//                    } catch (Exception e) {
//                        log.error("Error calling API: {}", e.getMessage());
//                        return List.of();
//                    }
//                }, executorService);

                results.add(future);



            }

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    results.toArray(new CompletableFuture[0])
            );

            allFutures.join();


            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);

            long totalProcessingTime = duration.toMillis();

            log.info("Total processing time: {} ms", totalProcessingTime);

            return totalProcessingTime;
        }
    }

}
