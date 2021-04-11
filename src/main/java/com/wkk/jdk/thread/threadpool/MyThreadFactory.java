package com.wkk.jdk.thread.threadpool;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weikunkun
 * @since 2021/4/11
 */
@Data
@Builder
public class MyThreadFactory implements ThreadFactory{
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public  MyThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread( r,namePrefix + threadNumber.getAndIncrement());
        if (t.isDaemon())
            t.setDaemon(true);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;

    }
}
