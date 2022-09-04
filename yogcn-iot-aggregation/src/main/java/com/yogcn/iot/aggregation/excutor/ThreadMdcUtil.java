package com.yogcn.iot.aggregation.excutor;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

public class ThreadMdcUtil {

    /**
     * 日志中的常量以及在MDC中的key值定义
     */
    private static final String TRACE_ID = "traceId";

    public static void setTraceIdIfAbsent() {
        insertMDC();
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * 向MDC中写入traceId
     */
    public static void insertMDC() {
        if (MDC.get(TRACE_ID) == null) {
            String uniqueId = UUIDGenerater.generateUUID();
            MDC.put(TRACE_ID, uniqueId);
        }
    }

    /**
     * 向MDC中写入traceId
     */
    public static void insertMDC(String traceId) {
        if (MDC.get(TRACE_ID) != null) {
            MDC.put(TRACE_ID, traceId);
        }
    }

    /**
     * 向MDC中删除traceId
     */
    public static void removeMDC() {
        MDC.remove(TRACE_ID);
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

    public static String getMdcTraceId() {

        return MDC.get(TRACE_ID);
    }
}
