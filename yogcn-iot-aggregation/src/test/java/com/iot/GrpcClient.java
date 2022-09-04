package com.iot;

import com.yogcn.iot.aggregation.excutor.MdcThreadPoolTaskExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.iot.emp.grpc.facade.DeviceManagementGrpc;
import com.iot.emp.grpc.facade.GDecodeRequest;
import com.iot.emp.grpc.facade.GEncodeRequest;
import com.iot.emp.grpc.facade.GJt808Response;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private final DeviceManagementGrpc.DeviceManagementBlockingStub stub;


    private MdcThreadPoolTaskExecutor processorsExecutor;

    public GrpcClient(Channel channel) {

        stub = DeviceManagementGrpc.newBlockingStub(channel);

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(" grpc-client-%d").build();
        this.processorsExecutor = new MdcThreadPoolTaskExecutor(1, 1, 0,
                1024, namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void test() {


        while (true) {

            processorsExecutor.execute(() -> {
                GEncodeRequest request = GEncodeRequest.newBuilder().setDeviceSn("130000088880").setTenantId("iotnps").setPlaintext("this is plaintext").build();
                //请在这里编写各自的业务逻辑

                GJt808Response result = stub.jt808Encode(request);

                System.out.println("encrypt data is : " + result);


                GDecodeRequest decode = GDecodeRequest.newBuilder().setDeviceSn("130000088880").setTenantId("iotnps").setCiphertext("this is ciphertext").build();

                GJt808Response decodeResponse = stub.jt808Decode(decode);
                System.out.println("decrypt data is : " + decodeResponse);
            });


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String user = "world";
        // Access a service running on the local machine on port 50051
        String target = "localhost:9000";
        // Allow passing in the user and target strings as command line arguments
        if (args.length > 0) {
            if ("--help".equals(args[0])) {
                System.err.println("Usage: [name [target]]");
                System.err.println("");
                System.err.println("  name    The name you wish to be greeted by. Defaults to " + user);
                System.err.println("  target  The server to connect to. Defaults to " + target);
                System.exit(1);
            }
            user = args[0];
        }
        if (args.length > 1) {
            target = args[1];
        }

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        try {
            GrpcClient client = new GrpcClient(channel);
            client.test();
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            try {
                channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

