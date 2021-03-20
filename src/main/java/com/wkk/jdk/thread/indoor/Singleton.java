package com.wkk.jdk.thread.indoor;

/**
 * @author weikunkun
 * @since 2021/3/20
 */
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {

    }

    private Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
