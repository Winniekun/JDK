package com.wkk.jdk.map.hashmap;

import java.util.HashMap;

/**
 * @Time: 20-3-18上午11:05
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class DebugForKnow {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "1");
        map.put(3, "1");
        map.remove(1);
        map.remove(3,"1");

        HashMap<Integer, String> map1 = new HashMap<>(20);

        HashMap<Integer, String> map2 = new HashMap<>(map1);

        HashMap<Integer, String> map3 = new HashMap<>(map);
    }
}
