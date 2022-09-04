package com.yogcn.iot.provider.facade;

import com.iot.emp.grpc.facade.DeviceManagementGrpc;
import com.iot.emp.grpc.facade.GDecodeRequest;
import com.iot.emp.grpc.facade.GEncodeRequest;
import com.iot.emp.grpc.facade.GJt808Response;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class DeviceManagementFacade extends DeviceManagementGrpc.DeviceManagementImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DeviceManagementFacade.class);

    @Override
    public void jt808Encode(GEncodeRequest request, StreamObserver<GJt808Response> responseObserver) {
        logger.info("EMQX 调用【加密】请求，deviceSn:{},tenantId:{},plaintext:{}", request.getDeviceSn(), request.getTenantId(), request.getPlaintext());

        GJt808Response response = GJt808Response.newBuilder().setCode("0000000000000").setStatus(true).setErrMsg("成功").setResult("this encrypted data").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void jt808Decode(GDecodeRequest request, StreamObserver<GJt808Response> responseObserver) {
        logger.info("EMQX 调用【解密】请求，deviceSn:{},tenantId:{},ciphertext:{}", request.getDeviceSn(), request.getTenantId(), request.getCiphertext());
        GJt808Response response = GJt808Response.newBuilder().setCode("0000000000000").setStatus(true).setErrMsg("成功").setResult("this decrypted data").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
