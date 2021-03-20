package com.wkk.jdk.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author weikunkun
 * @since 2021/3/19
 */
@Slf4j
public class FutureAndCallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = () -> {
            log.info("进入 callable 方法");

            Thread.sleep(1000);
            return "hello this is callable return value";
        };

        log.info("callable 提交到线程池");
        Future<String> future = executorService.submit(callable);
        log.info("主线程继续执行");
        while (!future.isDone()) {
            log.info("task still not done...");
            Thread.sleep(100);
        }
        String result = future.get();
        System.out.println(result);
        executorService.shutdown();
    }
}
