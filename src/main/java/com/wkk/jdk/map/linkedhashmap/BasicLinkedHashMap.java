package com.wkk.jdk.map.linkedhashmap;

import org.junit.Test;

import java.security.KeyStore;
import java.util.*;

/**
 * @Time: 2020/6/20下午7:25
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class BasicLinkedHashMap {
    public static void main(String[] args) {
//        Map<String, String> map = new LinkedHashMap<>();
//        for (int i = 0; i < 100; i++) {
//            map.put("name" + i, "json" + i);
//        }
//        System.out.println(map);
//        System.out.println();
//        map = new HashMap<>();
//        for (int i = 0; i < 100; i++) {
//            map.put("name" + i, "json" + i);
//        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        System.out.println(map);
    }

    @Test
    public void get() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String s = map.get("1");
    }

    /**
     * 理解插入和访问顺序的区别
     */
    @Test
    public void insetAndVisitedOrder() {
        Map<String, String> map = orderMap();
        System.out.println(map);
        System.out.println("=================");
        map.get("key-3");
        System.out.println(map);

    }

    public Map<String, String> orderMap() {
        Map<String, String> map = new LinkedHashMap<>(8, 0.75F, true);
        for (int i = 0; i < 5; i++) {
            map.put("key-" + i, "value-" + i);
        }
        return map;
    }
}
