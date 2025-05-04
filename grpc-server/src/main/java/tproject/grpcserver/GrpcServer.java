package tproject.grpcserver;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class GrpcServer {
    private Server server;

    @PostConstruct
    public void start() throws Exception {
        server = ServerBuilder.forPort(9090)
                .addService(new HelloServiceImpl())
                .build()
                .start();
        System.out.println("✅ gRPC Server started on port 9090");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC Server...");
            server.shutdown();
        }));
    }
}
