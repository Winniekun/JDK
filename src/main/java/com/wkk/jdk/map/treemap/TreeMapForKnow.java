package com.wkk.jdk.map.treemap;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author weikunkun
 * @since 2021/3/31
 */
public class TreeMapForKnow {
    @Test
    public void testAdd() {
        Map<Integer, Integer> map = new TreeMap<>();
        map.put(1000, 1);
        map.put(1, 11);
        map.put(2, 1);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
