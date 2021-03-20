package com.wkk.jdk.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author weikunkun
 * @since 2021/3/22
 */
@Slf4j
public class SingleThreadPool {

    private Worker task = new Worker();
    private MyFactory factory = new MyFactory(task);

    public static void main(String[] args) {
        SingleThreadPool plan = new SingleThreadPool();
//        ThreadFactoryBuilder builder = ThreadFactoryBuilder.builder().namePrefix("single-pool-name").build();
        ExecutorService pool = Executors.newSingleThreadExecutor(plan.factory);
        pool.execute(plan.task);
        pool.shutdown();
    }

    class MyFactory implements ThreadFactory {
        private Worker task;

        public MyFactory(Worker task) {
            super();
            this.task = task;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    ExecutorService pool = Executors.newSingleThreadExecutor(new MyFactory(task));
                    pool.execute(task);
                    pool.shutdown();
                }
            });
            return thread;
        }
    }

    public class Worker implements Runnable {
        private int task = 10;

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "--" + "启动");
            while (task > 0) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    log.info("error : {}", e.getMessage());
                }
                if (System.currentTimeMillis() % 3 == 0) {
                    throw new RuntimeException("模拟异常");
                }
                System.out.println(threadName + "--" + "执行task" + task);
                task--;
            }
            System.out.println(threadName + "--" + "正常终止");
        }
    }
}
