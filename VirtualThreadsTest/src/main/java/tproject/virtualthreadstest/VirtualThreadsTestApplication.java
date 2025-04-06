package tproject.virtualthreadstest;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class VirtualThreadsTestApplication {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreadsTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VirtualThreadsTestApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    @Bean
    public ExecutorService virtualThreadExecutor() {
        log.info("Initializing virtual thread executor");
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public Semaphore connectionSemaphore() {
        // Giới hạn ở mức 50 concurrent calls để tránh lỗi HTTP/2
        return new Semaphore(100);
    }

    @RestController
    static class TraditionalThreadsBenchmarkController {

        private final RestClient restClient;
        private final ExecutorService executorService;
        private final Semaphore semaphore;

        private static final String TARGET_ENDPOINT = "/posts";
        private static final int API_CALLS_PER_REQUEST = 5;

        public TraditionalThreadsBenchmarkController(
                RestClient restClient,
                ExecutorService traditionalExecutorService,
                Semaphore connectionSemaphore) {
            this.restClient = restClient;
            this.executorService = traditionalExecutorService;
            this.semaphore = connectionSemaphore;
        }

        @GetMapping("/api")
        public Map<String, Object> handleApiRequest() {
            log.info("Thread before API calls: {}", Thread.currentThread().getName());

            Instant start = Instant.now();

            List<CompletableFuture<Object>> results = new ArrayList<>();

            for (int i = 0; i < API_CALLS_PER_REQUEST; i++) {
                final int index = i;
                String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();

                CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
                            try {
                                // Chờ đợi semaphore với timeout
                                if (!semaphore.tryAcquire(2, TimeUnit.SECONDS)) {
                                    log.warn("Cannot acquire semaphore, too many concurrent requests");
                                    throw new RuntimeException("Cannot acquire semaphore");
                                }

                                try {
                                    log.info("Virtual thread call API {}: {}", index, Thread.currentThread().getName());
                                    return restClient.get()
                                            .uri(endpoint)
                                            .retrieve()
                                            .body(Object.class); // Deserialize thành Object thay vì PostDto[]
                                } catch (Exception e) {
                                    log.error("Error calling API {}: {}", index, e.getMessage());
                                    throw new RuntimeException("Error calling API", e);// Trả về object rỗng
                                } finally {
                                    semaphore.release();
                                }
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                log.error("Thread interrupted: {}", e.getMessage());
                                throw new RuntimeException("Thread interrupted");
                            } catch (RuntimeException e) {
                                log.error("Unexpected error: {}", e.getMessage());
                                throw new RuntimeException("Unexpected error");
                            }
                        }, executorService)
                        .orTimeout(5, TimeUnit.SECONDS)
                        .exceptionally(ex -> {
                            throw new RuntimeException("Unexpected error");
                        });

                results.add(future);
            }

            // Tổng hợp kết quả với timeout
            List<Object> allResults = new ArrayList<>();
            try {
                CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                        results.toArray(new CompletableFuture[0])
                );

                allFutures.get(10, TimeUnit.SECONDS);

                for (CompletableFuture<Object> result : results) {
                    Object res = result.getNow(new Object());
                    if (res != null) {
                        allResults.add(res);
                    }
                }
            } catch (Exception e) {

                log.error("Error waiting for all results: {}", e.getMessage());
                throw new RuntimeException("Error waiting for all results");
            }

            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            long totalProcessingTime = duration.toMillis();

            log.info("Total processing time: {} ms", totalProcessingTime);

            // Trả về response với nhiều thông tin hơn
            Map<String, Object> response = new HashMap<>();
            response.put("processingTimeMs", totalProcessingTime);
            response.put("resultsCount", allResults.size());

            return response;
        }
    }
}