//package tproject.tranditionaltest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestClient;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@SpringBootApplication
//public class TranditionalTestApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(TranditionalTestApplication.class, args);
//    }
//
//    private static final Logger log = LoggerFactory.getLogger(TranditionalTestApplication.class);
//
//    @Bean
//    public RestClient restClient() {
//        return RestClient.builder()
//                .baseUrl("https://jsonplaceholder.typicode.com")
//                .build();
//    }
//
//    @Bean
//    public ExecutorService traditionalExecutorService() {
//        int availableProcessors = Runtime.getRuntime().availableProcessors();
//        int threadPoolSize = availableProcessors * 10;
//        log.info("Initializing traditional thread pool with {} threads (based on {} processors)",
//                threadPoolSize, availableProcessors);
//        return Executors.newFixedThreadPool(threadPoolSize);
//    }
//
////    @Bean
////    public ExecutorService virtualThreadExecutor() {
////        log.info("Initializing virtual thread executor");
////        return Executors.newVirtualThreadPerTaskExecutor();
////    }
//
//
//    @RestController
//    static
//    class TraditionalThreadsBenchmarkController {
//
//        private final RestClient restClient;
//
//        private final ExecutorService executorService;
//
//        private static final String TARGET_ENDPOINT = "/posts";
//
//        private static final int API_CALLS_PER_REQUEST = 5;
//
//        public TraditionalThreadsBenchmarkController(RestClient restClient
//                , ExecutorService traditionalExecutorService) {
//            this.restClient = restClient;
//            this.executorService = traditionalExecutorService;
//        }
//
//        /**
//         * Simple benchmark endpoint that makes multiple API calls
//         * for each client request using traditional threads
//         */
//        @GetMapping("/api")
//        public long handleApiRequest() {
//
//            log.info("Thread before API calls: {}", Thread.currentThread().getName());
//
//            Instant start = Instant.now();
//
//            List<CompletableFuture<Object>> results = new ArrayList<>();
//
//            for (int i = 0; i < API_CALLS_PER_REQUEST; i++) {
//
//                String endpoint = TARGET_ENDPOINT + "?requestId=" + System.nanoTime();
//
//                CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
//					log.info("Async thread call API call: {}", Thread.currentThread().getName());
//					return restClient.get()
//							.uri(endpoint)
//							.retrieve()
//							.body(Object.class);
//				}, executorService);
//
////                CompletableFuture<List<PostDto>> future = CompletableFuture.supplyAsync(() -> {
////                    try {
////                        log.info("Virtual thread call API: {}", Thread.currentThread().getName());
////                        PostDto[] posts = restClient.get()
////                                .uri(endpoint)
////                                .retrieve()
////                                .body(PostDto[].class);
////                        return Arrays.asList(posts);
////                    } catch (Exception e) {
////                        log.error("Error calling API: {}", e.getMessage());
////                        return List.of();
////                    }
////                }, executorService);
//
//                results.add(future);
//
//
//
//            }
//
//            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
//                    results.toArray(new CompletableFuture[0])
//            );
//
//            allFutures.join();
//
//
//            Instant end = Instant.now();
//            Duration duration = Duration.between(start, end);
//
//            long totalProcessingTime = duration.toMillis();
//
//            log.info("Total processing time: {} ms", totalProcessingTime);
//
//            return totalProcessingTime;
//        }
//    }
//
//}



package tproject.tranditionaltest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tproject.common.HelloRequest;
import tproject.common.HelloResponse;
import tproject.common.HelloServiceGrpc;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
    public ExecutorService traditionalExecutorService() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int threadPoolSize = availableProcessors * 10;
        log.info("Initializing traditional thread pool with {} threads (based on {} processors)",
                threadPoolSize, availableProcessors);
        return Executors.newFixedThreadPool(threadPoolSize);
    }

    @Bean
    public ManagedChannel grpcChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
    }

    @Bean
    public HelloServiceGrpc.HelloServiceBlockingStub grpcStub(ManagedChannel channel) {
        return HelloServiceGrpc.newBlockingStub(channel);
    }

    @RestController
    static class GrpcClientBenchmarkController {

        private final ExecutorService executorService;
        private final HelloServiceGrpc.HelloServiceBlockingStub grpcStub;

        private static final int API_CALLS_PER_REQUEST = 5;

        public GrpcClientBenchmarkController(ExecutorService traditionalExecutorService,
                                             HelloServiceGrpc.HelloServiceBlockingStub grpcStub) {
            this.executorService = traditionalExecutorService;
            this.grpcStub = grpcStub;
        }

        @GetMapping("/grpc")
        public long callGrpcBenchmark() {

            log.info("Thread before gRPC calls: {}", Thread.currentThread().getName());

            Instant start = Instant.now();
            List<CompletableFuture<HelloResponse>> results = new ArrayList<>();

            for (int i = 0; i < API_CALLS_PER_REQUEST; i++) {
                final String name = "User-" + System.nanoTime();
                CompletableFuture<HelloResponse> future = CompletableFuture.supplyAsync(() -> {
                    log.info("Calling gRPC in thread: {}", Thread.currentThread().getName());
                    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
                    return grpcStub.sayHello(request);
                }, executorService);

                results.add(future);
            }

            CompletableFuture<Void> all = CompletableFuture.allOf(results.toArray(new CompletableFuture[0]));
            all.join();

            Instant end = Instant.now();
            long duration = Duration.between(start, end).toMillis();
            log.info("Total gRPC processing time: {} ms", duration);
            return duration;
        }
    }
}
