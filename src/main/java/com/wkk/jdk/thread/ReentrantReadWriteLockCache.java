package com.wkk.jdk.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 模拟使用缓存
 *
 * @author weikunkun
 * @since 2021/3/19
 */
public class ReentrantReadWriteLockCache {
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock rl = lock.readLock();
    private static Lock wl = lock.writeLock();
    private static final Map<String, Object> map = new HashMap<>();

    public static final Object get(String key) {
        rl.lock();
        try {
            return map.get(key);
        } finally {
            rl.unlock();
        }
    }

    public static final void set(String key, Object value) {
        wl.lock();
        try {
            map.put(key, value);
        } finally {
            wl.unlock();
        }
    }

    public static void main(String[] args) {
        int SHARED_SHIFT   = 16;
        int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
        int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;
        int c = 10;
        System.out.println(c >>> SHARED_SHIFT);
        System.out.println(c & EXCLUSIVE_MASK);
        System.out.println(14325 & EXCLUSIVE_MASK);

    }
}
