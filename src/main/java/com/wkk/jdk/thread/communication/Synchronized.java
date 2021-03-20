package com.wkk.jdk.thread.communication;

/**
 * @author weikunkun
 * @since 2021/3/20
 */
public class Synchronized {
    public static void main(String[] args) {
        synchronized (Synchronized.class) {
            m();
        }
    }

    public static synchronized void m() {

    }
}
