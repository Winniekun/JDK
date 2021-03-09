package com.wkk.jdk.map.linkedhashmap;

import java.util.*;

/**
 * @Time: 2020/6/20下午7:25
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class BasicLinkedHashMap {
    public static void main(String[] args) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name1", "json1");
        map.put("name2", "json2");
        map.put("name3", "json3");
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
