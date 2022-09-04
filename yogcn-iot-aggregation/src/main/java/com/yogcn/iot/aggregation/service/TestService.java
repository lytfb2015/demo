package com.yogcn.iot.aggregation.service;

import com.yogcn.iot.aggregation.excutor.MdcThreadPoolTaskExecutor;
import com.yogcn.iot.aggregation.response.HelloResponse;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.iot.emp.grpc.facade.DeviceManagementGrpc;
import com.iot.emp.grpc.facade.GDecodeRequest;
import com.iot.emp.grpc.facade.GEncodeRequest;
import com.iot.emp.grpc.facade.GJt808Response;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Slf4j
public class TestService {

    @GrpcClient("yogcn-iot-provider")
    private DeviceManagementGrpc.DeviceManagementBlockingStub stub;

    private MdcThreadPoolTaskExecutor processorsExecutor;

    @PostConstruct
    public void init() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(" mqtt-inbound-receiver-%d").build();
        this.processorsExecutor = new MdcThreadPoolTaskExecutor(1, 1, 0,
                1024, namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public List<HelloResponse> hello() {

        while (true) {

            processorsExecutor.execute(() -> {
                GEncodeRequest request = GEncodeRequest.newBuilder().setDeviceSn("130000088880").setTenantId("iotnps").setPlaintext("this is plaintext").build();
                //请在这里编写各自的业务逻辑

                GJt808Response result = stub.jt808Encode(request);

                log.info("encrypt data is : {}", result);


                GDecodeRequest decode = GDecodeRequest.newBuilder().setDeviceSn("130000088880").setTenantId("iotnps").setCiphertext("this is ciphertext").build();

                GJt808Response decodeResponse = stub.jt808Decode(decode);
                log.info("decrypt data is : {}", decodeResponse);
            });


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
