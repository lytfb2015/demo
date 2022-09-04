package com.yogcn.iot.aggregation.excutor;

import java.util.UUID;

/**
 * UUID生成器
 * @author Peter.Feng
 */
public class UUIDGenerater {

    public static final String generateUUID(){

        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        return uniqueId;
    }
}
