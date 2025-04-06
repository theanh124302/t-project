package tproject.virtualthreadstest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
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
import java.util.concurrent.Executors;

@SpringBootApplication
public class VirtualThreadsTestApplication {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreadsTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VirtualThreadsTestApplication.class, args);
    }

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }

    // RestClient bean
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    @RestController
    static
    class VirtualThreadsBenchmarkController {

        private final RestClient restClient;

        // Target endpoint from JSONPlaceholder
        private static final String TARGET_ENDPOINT = "/posts";

        // Number of external API calls to make per client request
        private static final int API_CALLS_PER_REQUEST = 5;

        public VirtualThreadsBenchmarkController(RestClient restClient) {
            this.restClient = restClient;
        }

        /**
         * Simple benchmark endpoint that makes multiple API calls
         * for each client request
         */
        @GetMapping("/api")
        public Map<String, Object> handleApiRequest() {
            Instant start = Instant.now();

            log.info("Thread before API calls: {}", Thread.currentThread().getName());

            List<Object> results = new ArrayList<>();

            Instant requestStart;

            Instant responseTime;

            // Each client request triggers multiple API calls to external service
            for (int i = 0; i < API_CALLS_PER_REQUEST; i++) {
                // Add a query parameter to avoid caching

                log.info("Thread during API call {}: {}", i, Thread.currentThread().getName());

                String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();

                requestStart = Instant.now();

                Object response = restClient.get()
                        .uri(endpoint)
                        .retrieve()
                        .body(Object.class);

                responseTime = Instant.now();

                log.info(("api {} call took {} ms"),i,  Duration.between(requestStart, responseTime).toMillis());

                log.info("Thread after API call {}: {}", i, Thread.currentThread().getName());

                results.add(response);

            }

            log.info("Thread after API calls: {}", Thread.currentThread().getName());

            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);

            log.info("Total processing time: {} ms", duration.toMillis());

            Map<String, Object> response = new HashMap<>();
            response.put("serverProcessingTimeMs", duration.toMillis());
            response.put("externalApiCallsMade", API_CALLS_PER_REQUEST);

            return response;
        }

    }

}

