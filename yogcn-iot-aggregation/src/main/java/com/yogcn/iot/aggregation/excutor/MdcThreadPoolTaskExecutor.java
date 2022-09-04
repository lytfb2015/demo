package com.yogcn.iot.aggregation.excutor;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * 包含有traceId的线程池
 *
 * @author Peter.Feng
 * @date 2020/07/27
 */
public class MdcThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    public MdcThreadPoolTaskExecutor() {
        super.setCorePoolSize(10);
        super.initialize();
    }

    public MdcThreadPoolTaskExecutor(ThreadFactory threadFactory) {
        super.setCorePoolSize(10);
        super.initialize();
        super.setThreadFactory(threadFactory);
    }

    public MdcThreadPoolTaskExecutor(int corePoolSize,
                                     int maximumPoolSize,
                                     int keepAliveTime,
                                     int workQueueNum,
                                     ThreadFactory threadFactory,
                                     RejectedExecutionHandler handler){
        super.setCorePoolSize(corePoolSize);
        super.setMaxPoolSize(maximumPoolSize);
        super.setKeepAliveSeconds(keepAliveTime);
        super.setQueueCapacity(workQueueNum);
        super.setThreadFactory(threadFactory);
        super.setRejectedExecutionHandler(handler);
        super.initialize();
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }


    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
