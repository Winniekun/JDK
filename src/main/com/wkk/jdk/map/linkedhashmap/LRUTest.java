package com.wkk.jdk.map.linkedhashmap;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @Time: 2020/6/20下午8:31
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class LRUTest {
    public static void main(String[] args) {
        LRU<Integer, Integer> lru = new LRU<>(4, 0.75f);
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.put(4, 4);
        lru.put(5, 5);
        lru.put(6, 6);
        lru.put(7, 7);
        System.out.println(lru.get(1));
        lru.put(666, 666);
        System.out.println(lru);

    }
}

class LRU<K, V> extends LinkedHashMap<K, V> {
    // 保存缓存的容量
    private int capacity;

    public LRU(int capacity, float loadFactory) {
        super(capacity, loadFactory);
        this.capacity = capacity;
    }

    /**
     * 重写removeEldestEntry()方法设置何时移除旧元素
     *
     * @param eldest
     * @return boolean
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this.capacity;
    }
}
