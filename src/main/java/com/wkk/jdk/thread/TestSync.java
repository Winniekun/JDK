package com.wkk.jdk.thread;


/**
 * @author weikunkun
 * @since 2021/3/18
 */
public class TestSync {
    private int number = 0;

    public void addNumber() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                number++;
                System.out.println("current: " + number + " " +  Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        TestSync sync = new TestSync();
        Thread a = new Thread(()-> sync.addNumber());
        Thread b = new Thread(()-> sync.addNumber());
        a.start();
        b.start();

    }
}
