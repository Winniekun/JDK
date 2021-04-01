package com.wkk.jdk.map.concurrenthashmap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap 基础API
 *
 * @author weikunkun
 * @since 2021/3/26
 */
public class ConcurrentHashMapForKnow {
    @Test
    public void testAdd() {
        Map<Integer, String> conMap = new ConcurrentHashMap<>();
        conMap.put(100,"Hello");
        conMap.put(101, "Wkk");
        conMap.put(102,"Geeks");

        conMap.putIfAbsent(101, "hello");
        conMap.remove(101, "Wkk");
        conMap.putIfAbsent(103, "Hello");

        System.out.println(conMap);
    }

    @Test
    public void testIterator() {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(100, 1);
        map.put(101, 2);
        map.put(102, 3);
        map.put(103, 4);
        System.out.println(map);
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            System.out.println(next.getKey() + ", " + next.getValue());
        }
    }

    @Test
    public void testBitOperation() {
        int a = 7;
        int b = 10;
        System.out.println(b % 8);
        System.out.println(b & a);
    }

    @Test
    public void testThreadPool() {
        List<Integer> list = new ArrayList<>();
        List<String> strs = new ArrayList<>();
        System.out.println(list.getClass().equals(strs.getClass()));
    }
}
