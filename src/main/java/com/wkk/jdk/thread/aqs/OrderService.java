package com.wkk.jdk.thread.aqs;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author weikunkun
 * @since 2021/4/5
 */
public class OrderService {
    public static ReentrantLock reentrantLock  = new ReentrantLock();

    public void createOrder() {
        reentrantLock.lock();
        try {
            // do something
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
    }
}
