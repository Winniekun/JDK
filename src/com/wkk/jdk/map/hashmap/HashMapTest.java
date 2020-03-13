package com.wkk.jdk.map.hashmap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Time: 19-11-20下午7:13
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap<String, String > map = new HashMap<>();
        // 键不能重复 其类似于Python的字典
        map.put("san", "张三");
        map.put("si", "李四");
        map.put("wu", "王五");
        System.out.println(map);
        // {san=张三, si=李四, wu=王五}
        map.put("wu", "老王"); // 覆盖(只会覆盖值, 键不会)
        System.out.println(map);
        map.put("lao", "老王");
        System.out.println(map);
        /**
         * 遍历map
         */
        System.out.println("-----------for each循环获取键-----------------");
        // 1 获取map中所有的键
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.print(key+" ");

        }
        System.out.println();
        // 2 获取map中所有的值
        System.out.println();
        System.out.println("-----------for each循环获取值-----------------");
        Collection<String> values = map.values();
        for (String value : values) {
            System.out.print(value + " ");
        }

        System.out.println();

        // 3 得到key的值的同时得到key所对应的值
        System.out.println("-----------得到key值之后获取对应的值-----------------");
        Set<String> kys = map.keySet();
        for (String ky : kys) {
            System.out.print(ky + ": " + map.get(ky) + " ");
        }
        System.out.println();
        // 另外一种不常用遍历
        Set<Map.Entry<String , String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.print(entry.getKey() + "---" + entry.getValue() + " ");


        }
        /**
         * hashmap 其他常用的方法
         */
        System.out.println("after map.size():"  + map.size());
        System.out.println("after map.isEmpty()" + map.isEmpty());
        System.out.println(map.remove("san"));
        System.out.println("after remove: "+ map);
        System.out.println("after map.get(si): " + map.get("si"));
        System.out.println("after map.containsKey(si): " + map.containsKey("si"));
        System.out.println("after map.containsValue(李四): "+ map.containsValue("李四"));
        System.out.println(map.replace("si", "李四2"));
        System.out.println("after map.replace(si, 李四2): "+ map);
    }

}
