package com.wkk.jdk.thread.threadpool;

import javafx.beans.binding.ObjectExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池的异常处理
 *
 * @author weikunkun
 * @since 2021/4/11
 */
@Slf4j
public class ExceptionThreadPool {
    public static void main(String[] args) {
//        ExecutorService threadPoolFixed = Executors.newFixedThreadPool(10);
//        Thread thread = new Thread(()-> {
//            int i = 1/0;
//        });
//        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                log.error(t+ " throw exception: " + e);
//            }
//        });
//        threadPoolFixed.submit(thread);
//        threadPoolFixed.shutdown();


//        threadPoolFixed.submit(()-> {
//            handle();
//        });
//        threadPoolFixed.shutdown();
//        for (int i = 0; i < 5; i++) {
//            threadPoolFixed.execute(()->{
////                handle();
//                handler();
//            });
//        }
//        threadPoolFixed.shutdown();


        //如果是线程池的模式:
//        ExecutorService threadPool = Executors.newFixedThreadPool(1, r -> {
//            Thread t = new Thread(r);
//            t.setUncaughtExceptionHandler(
//                    (t1, e) -> log.error(t1 + " throws exception: " + e));
//            return t;
//        });
//        ExecutorService threadNoCatch = Executors.newFixedThreadPool(2);
////        threadPool.execute(()-> handler());
////        threadPool.shutdown();
//
//        threadNoCatch.shutdown();

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        threadPool.submit(() -> {
            Object obj = null;
            System.out.println(obj.toString());
        });
        threadPool.execute(() -> {
            Object obj = null;
            System.out.println(obj.toString());
        });

    }

    public static void handle() {
        try {
            Object obj = null;
            System.out.println(obj.toString());
        } catch (Exception e) {
            log.error("some thing error", e.getMessage());
        }
    }

    public static void handler() {
        Object obj = null;
        System.out.println(obj.toString());
    }
}
