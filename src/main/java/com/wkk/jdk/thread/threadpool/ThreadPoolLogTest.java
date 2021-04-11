package com.wkk.jdk.thread.threadpool;



import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author weikunkun
 * @since 2021/4/11
 */
@Slf4j
public class ThreadPoolLogTest {
    private static final long KEEP_ALIVE_TIME = 60L;

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = poolExecutor(1, 1);
        log.info("开始提交任务");
        executor.execute(ThreadPoolLogTest::baseDoSomething);
//        Future<Result> task = executor.submit(ThreadPoolLogTest::doSomethingI);
        log.info("提交任务完成");
        executor.shutdown();
//        while (!task.isDone()) {
//            TimeUnit.MILLISECONDS.sleep(100);
//        }
//        log.info("任务执行结果：{}", task.get().ok);

    }

    private static void baseDoSomething() {
        int value = 10 / 0;
    }

    private static void doSomething() {
        log.info("处理当前的任务 " + Thread.currentThread().getName());
        try {
            int value = 10 / 0;
        } catch (Exception e) {
            log.info("出现错误 {}", e.getMessage());
        }
        log.info("处理后序的任务 ");
    }

    private static Result doSomethingI() {
        log.info("处理当前的任务 " + Thread.currentThread().getName());
        int value = 10 / 0;
        log.info("处理后序的任务 ");
        return new Result(true);

    }

    static class Result {
        boolean ok;

        Result(boolean ok) { this.ok = ok; }

        @Override
        public String toString() { return "Result{" + "ok=" + ok + '}'; }
    }

    public static ThreadPoolExecutor poolExecutor(int core, int max) {
//        return new ThreadPoolExecutor(core, max, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(),
//                MyThreadFactory.builder().namePrefix("exception-thread-pool").build(),
//                new ThreadPoolExecutor.AbortPolicy());
        // 自定义线程池
//        return new CustomThreadPoolExecutor(core, max, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
        // 自定义所在线程的group
//        return new ThreadPoolExecutor(core, max, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(), r -> new Thread(new CustomThreadGroup("CustomThreadGroup"), r),
//                new ThreadPoolExecutor.AbortPolicy());
        // 设置 UncaughtExceptionHandler
        return new ThreadPoolExecutor(core, max, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), r -> {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler((t, e) ->
                    log.error("CustomThreadPoolExecutor execute Exception: {}",
                            ThreadUtils.stackTrace(e.getStackTrace())));
            return thread;
        }, new ThreadPoolExecutor.AbortPolicy());

    }

}
