package tproject.grpcserver;

import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String name = request.getName();
        HelloResponse response = HelloResponse.newBuilder()
                .setMessage("Xin chào, " + name)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
