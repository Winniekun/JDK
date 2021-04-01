package com.wkk.jdk.map.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Time: 20-3-18上午11:05
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class DebugForKnow {
    public static void main(String[] args) {
       HashMap<Integer, Integer> map = new HashMap<>();
       map.put(1,1);
        System.out.println(5 & 15);
        System.out.println(5 & 31);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            map.containsKey(entry.getKey());
        }
        List<Integer> list = new ArrayList<>();
    }
}
