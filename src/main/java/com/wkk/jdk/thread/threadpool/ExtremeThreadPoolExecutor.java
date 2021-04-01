package com.wkk.jdk.thread.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 一种弹性线程池
 *
 * @author weikunkun
 * @since 2021/4/1
 */
public class ExtremeThreadPoolExecutor extends ThreadPoolExecutor {

    public ExtremeThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 自定义阻塞队列
     *
     * @param <Runnable>
     */
    static class ExtremeBlockQueue<Runnable> extends LinkedBlockingQueue<Runnable> {
        public ExtremeBlockQueue(int capacity) {
            super(capacity);
        }

        @Override
        public boolean offer(Runnable runnable) {
            return false;
        }

        public boolean extremeOffer(Runnable runnable) {
            return super.offer(runnable);
        }
    }

    static class ExtremePolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 线程池未关闭
            if (!executor.isShutdown()) {
                //真正入阻塞队列，若阻塞队列已满，则抛出RejectedExecutionException
                if (!((ExtremeBlockQueue)executor.getQueue()).extremeOffer(r)) {
                    throw new RejectedExecutionException("Task " + r.toString() +
                            " rejected from " +
                            executor.toString());
                }
            }
        }
    }
}
