package tproject.tauthservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/test")
public class CompletableFutureLearning {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @GetMapping("/completable-future")
    public ResponseEntity<String> test() throws InterruptedException, ExecutionException {
        log.info("Bắt đầu endpoint: {}", Thread.currentThread().getName());

        // 1. Tạo CompletableFuture từ supplyAsync
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 1 đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Operation 1 hoàn tất");
            return "Kết quả từ Operation 1";
        }, executorService);

        log.info("Thread chính vẫn chạy bình thường : {}", Thread.currentThread().getName());

        // 2. Tạo CompletableFuture từ runAsync
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 2 đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Operation 2 hoàn tất");
        }, executorService);

        log.info("Thread chính vẫn chạy bình thường : {}", Thread.currentThread().getName());

        // 3. Biến đổi kết quả với thenApply
        CompletableFuture<Integer> future3 = future1.thenApply(result -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 3 (thenApply) đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return result.length();
        });

        log.info("Thread chính vẫn chạy bình thường : {}", Thread.currentThread().getName());

        // 4. Thực hiện thenAccept để sử dụng kết quả
        CompletableFuture<Void> future4 = future3.thenAccept(length -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 4 (thenAccept) đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Độ dài của chuỗi: {}", length);
        });

        log.info("Thread chính vẫn chạy bình thường : {}", Thread.currentThread().getName());

        // 5. Tạo CompletableFuture với thenCompose (flat map)
        CompletableFuture<String> future5 = future1.thenCompose(result -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 5 (thenCompose) đang chạy trên thread: {}", threadName);
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return result + " + Kết quả từ Operation 5";
            }, executorService);
        });

        log.info("Thread chính vẫn chạy bình thường : {}", Thread.currentThread().getName());

        // 6. Kết hợp 2 CompletableFuture độc lập với thenCombine
        CompletableFuture<String> future6 = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 6.1 đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Data từ Operation 6.1";
        }, executorService).thenCombine(CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 6.2 đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Data từ Operation 6.2";
        }, executorService), (result1, result2) -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 6.3 (thenCombine) đang chạy trên thread: {}", threadName);
            return result1 + " + " + result2;
        });

        log.info("Thread chính vẫn chạy bình thường : {}", Thread.currentThread().getName());

        // 7. Chờ nhiều CompletableFuture với allOf
        List<CompletableFuture<?>> allFutures = new ArrayList<>();
        allFutures.add(future1);
        allFutures.add(future2);
        allFutures.add(future3);
        allFutures.add(future4);
        allFutures.add(future5);
        allFutures.add(future6);



        CompletableFuture<Void> allOf = CompletableFuture.allOf(
                allFutures.toArray(new CompletableFuture[0])
        );

        log.info("Thread chính vẫn chạy bình thường : {}", Thread.currentThread().getName());

        // 8. Xử lý lỗi với exceptionally
        CompletableFuture<Object> futureWithError = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 8 (có lỗi) đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("Lỗi có chủ ý!");
        }, executorService).exceptionally(ex -> {
            String threadName = Thread.currentThread().getName();
            log.info("Xử lý lỗi đang chạy trên thread: {}", threadName);
            return "Đã xử lý lỗi: " + ex.getMessage();
        });

        log.info("Thread chính vẫn chạy bình thường : {}", Thread.currentThread().getName());

        // 9. Thực hiện với kết quả hoặc xử lý lỗi với handle
        CompletableFuture<String> futureWithHandle = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Operation 9 đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (Math.random() > 0.5) {
                throw new RuntimeException("Lỗi ngẫu nhiên!");
            }
            return "Kết quả thành công từ Operation 9";
        }, executorService).handle((result, ex) -> {
            String threadName = Thread.currentThread().getName();
            log.info("Handle đang chạy trên thread: {}", threadName);
            if (ex != null) {
                return "Handle xử lý lỗi: " + ex.getMessage();
            }
            return "Handle xử lý kết quả: " + result;
        });

        log.info("Thread chính vẫn chạy bình thường trước khi get : {}", Thread.currentThread().getName());

        // 10. CompletableFuture với timeout
        CompletableFuture<String> futureWithTimeout = CompletableFuture.supplyAsync(() -> {
                    String threadName = Thread.currentThread().getName();
                    log.info("Operation 10 đang chạy trên thread: {}", threadName);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "Kết quả từ Operation 10";
                }, executorService).orTimeout(7000, TimeUnit.MILLISECONDS)
                .exceptionally(ex -> "Timeout hoặc lỗi: " + ex.getMessage());

        // Chờ tất cả các CompletableFuture hoàn thành
        allOf.join();
        String result8 = (String) futureWithError.get();
        String result9 = futureWithHandle.get();
        String result10 = futureWithTimeout.get();

        log.info("Tất cả CompletableFuture đã hoàn thành");
        log.info("Kết quả từ future8: {}", result8);
        log.info("Kết quả từ future9: {}", result9);
        log.info("Kết quả từ future10: {}", result10);

        return ResponseEntity.ok("CompletableFuture Test hoàn thành. Kiểm tra logs để xem chi tiết.");
    }

    // Thêm một endpoint để thử nghiệm anyOf - chờ bất kỳ future nào hoàn thành trước
    @GetMapping("/completable-future-anyof")
    public ResponseEntity<String> testAnyOf() throws InterruptedException, ExecutionException {
        log.info("Bắt đầu endpoint anyOf: {}", Thread.currentThread().getName());

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("AnyOf-1 đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Kết quả từ future1 (5s)";
        }, executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("AnyOf-2 đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(3000); // Hoàn thành sớm hơn
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Kết quả từ future2 (3s)";
        }, executorService);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("AnyOf-3 đang chạy trên thread: {}", threadName);
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Kết quả từ future3 (7s)";
        }, executorService);

        // anyOf trả về kết quả của CompletableFuture hoàn thành đầu tiên
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);

        // Chờ kết quả
        Object result = anyOf.get();
        log.info("Kết quả từ anyOf: {}", result);

        return ResponseEntity.ok("Kết quả anyOf: " + result +
                " (future2 sẽ hoàn thành trước do chỉ sleep 3s)");
    }

    // Endpoint để thử nghiệm CompletableFuture.complete thủ công
    @GetMapping("/completable-future-manual")
    public ResponseEntity<String> testManualCompletion() throws InterruptedException, ExecutionException {
        log.info("Bắt đầu endpoint manual completion: {}", Thread.currentThread().getName());

        CompletableFuture<String> manualFuture = new CompletableFuture<>();

        // Start một thread để hoàn thành CompletableFuture sau 5 giây
        executorService.submit(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Thread xử lý manual future: {}", threadName);
            try {
                Thread.sleep(5000);
                log.info("Hoàn thành manual future");
                manualFuture.complete("Kết quả từ manual completion");

                // Nếu muốn hoàn thành với lỗi:
                // manualFuture.completeExceptionally(new RuntimeException("Lỗi từ manual completion"));
            } catch (InterruptedException e) {
                manualFuture.completeExceptionally(e);
            }
        });

        // Chờ kết quả
        String result = manualFuture.get();
        log.info("Kết quả từ manual future: {}", result);

        return ResponseEntity.ok("Manual completion test thành công: " + result);
    }

    @GetMapping("/completable-future-nonblocking")
    public CompletableFuture<ResponseEntity<String>> testNonBlocking() {
        log.info("Bắt đầu endpoint non-blocking: {}", Thread.currentThread().getName());

        return CompletableFuture.supplyAsync(() -> {
                    String threadName = Thread.currentThread().getName();
                    log.info("Operation đang chạy trên thread: {}", threadName);
                    try {
                        Thread.sleep(5000); // Mô phỏng xử lý dài
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "Kết quả từ xử lý bất đồng bộ";
                }, executorService)
                .thenApply(result -> {
                    log.info("Biến đổi kết quả trên thread: {}", Thread.currentThread().getName());
                    return ResponseEntity.ok("Xử lý non-blocking hoàn thành: " + result);
                });
    }

}