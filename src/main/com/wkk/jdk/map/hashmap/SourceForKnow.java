package com.wkk.jdk.map.hashmap;

import java.util.HashMap;

/**
 * @Time: 19-12-2下午3:39
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class SourceForKnow {
    private static int MAXIMUM_CAPACITY = 1<<30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        n = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        return n;
    }

    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.hashCode();

//        HashMap map = new HashMap(100);
//        System.out.println(map.size());

//        Class<HashMap> aClass = HashMap.class;
//
//        HashMap map1 = aClass.newInstance();
//
//        map1.put("1", 1);
//        map1.put("2", 2);
//        Field loadFactor = aClass.getDeclaredField("loadFactor");
//        Field threshold = aClass.getDeclaredField("threshold");
//        loadFactor.setAccessible(true);
//        threshold.setAccessible(true);
//        System.out.println(map1);
//        System.out.println(threshold);



//        int n = -1;
//        System.out.println(n);
//        System.out.println(tableSizeFor(n));
//        int i = tableSizeFor(2);
//        System.out.println(i);

//        int h;
//        Object m = "fdsafa";
//        // ^ ：按位异或
//        // >>>:无符号右移，忽略符号位，空位都以0补齐
//        h = m.hashCode();
//        System.out.println(h);
//        System.out.println(h >>> 16);
//        System.out.println(4>>2);
//        System.out.println(28>>3);


    }
}
