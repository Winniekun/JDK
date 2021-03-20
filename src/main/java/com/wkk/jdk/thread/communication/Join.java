package com.wkk.jdk.thread.communication;

import lombok.SneakyThrows;

/**
 * @author weikunkun
 * @since 2021/3/20
 */
public class Join {
    public static void main(String[] args) throws InterruptedException {
        Thread previous = Thread.currentThread();
        Thread thread = new Thread(new Demo(previous));

        thread.start();
        thread.join(199);
        System.out.println(previous.getState());

    }

    private static class Demo implements Runnable {
        int a = 1000;
        Thread thread;
        public Demo(Thread thread) {
            this.thread = thread;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                a += i * 5;
            }
            System.out.println(thread.getState());
            Thread.sleep(1000);
        }
    }
}
