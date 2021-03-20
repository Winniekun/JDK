package com.wkk.jdk.thread;

/**
 * @author weikunkun
 * @since 2021/3/19
 */
public class ThreadStatus {
    private static ThreadStatus threadStatus = new ThreadStatus();

    public static void main(String[] args) throws InterruptedException {
//        blocked();
        Thread main = Thread.currentThread();
        Thread waiting = new Thread(()-> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            System.out.println(main.getState());
        });

        waiting.start();
        waiting.join();

        Thread terminated = new Thread(()->{});
        terminated.start();
        Thread.sleep(1000);
        System.out.println(terminated.getState());

    }

    public static void waiting() {


    }

    public static void blocked() throws InterruptedException {
        Thread a = new Thread(()->{
            threadStatus.commonResource();
        });

        Thread b = new Thread(()->{
            threadStatus.commonResource();
        });

        a.start();
        b.start();

        Thread.sleep(1);
        System.out.println(b.getState());
        System.exit(0);
    }

    public synchronized void commonResource() {
        while (true) {

        }
    }
}
