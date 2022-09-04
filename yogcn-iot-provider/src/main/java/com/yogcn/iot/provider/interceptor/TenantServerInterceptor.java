package com.yogcn.iot.provider.interceptor;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcGlobalServerInterceptor
public class TenantServerInterceptor implements ServerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TenantServerInterceptor.class);

    static final Metadata.Key<String> CUSTOM_HEADER_KEY =
            Metadata.Key.of("tenantId", Metadata.ASCII_STRING_MARSHALLER);


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        logger.info("grpc请求，头数据：{}", headers.get(CUSTOM_HEADER_KEY));
        return next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendHeaders(Metadata responseHeaders) {
                responseHeaders.put(CUSTOM_HEADER_KEY, "customRespondValue");
                super.sendHeaders(responseHeaders);
            }
        }, headers);

    }
}
