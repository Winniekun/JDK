package com.wkk.jdk.map.hashmap;


import java.util.ArrayList;
import java.util.List;

/**
 * 自己实现一个hashmap
 * @author weikunkun
 * @since 2021/3/9
 */
public class OwnMap {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("wkk");
        list.add("kkw");
        list.add("kwk");
        String[] table = new String[4];
        for (String key : list) {
            int idx = key.hashCode() & (table.length-1);
            if (null == table[idx]) {
                table[idx] = key;
                continue;
            }
            table[idx] = table[idx] + "->" + key;
        }


    }
}
