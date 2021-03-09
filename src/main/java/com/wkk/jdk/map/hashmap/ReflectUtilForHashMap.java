package com.wkk.jdk.map.hashmap;

import org.omg.CORBA.INTERNAL;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.*;

/**
 * @Time: 20-3-13上午11:20
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class ReflectUtilForHashMap {
    public static Class<?> entryClass;
    public static Field table;
    public static Field size;
    public static Field threshold;
    public static Field loadFactor;
    public static Constructor constructor1;
    public static Constructor constructor2;
    public static Constructor constructor3;
    public static Constructor constructor4;
    private static Random random = new Random();
    private static int range = 100;


    static {
        try {
            entryClass = Class.forName("java.util.HashMap");
            size = entryClass.getDeclaredField("size");
            table = entryClass.getDeclaredField("table");
            table.setAccessible(true);
            size.setAccessible(true);
            threshold = entryClass.getDeclaredField("threshold");
            threshold.setAccessible(true);
            loadFactor = entryClass.getDeclaredField("loadFactor");
            loadFactor.setAccessible(true);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IllegalAccessException {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "1");
        map.put(3, "1");
        System.out.println("thread " + threshold.get(map));
        System.out.println("负载因子" + loadFactor.get(map));
        System.out.println("size: " + size.get(map));
        System.out.println(table.get(map) == null);
        System.out.println("######################");
        HashMap<Integer, String> map1 = new HashMap<>(20);
        System.out.println("thread " + threshold.get(map1));
        System.out.println("负载因子" + loadFactor.get(map1));
        System.out.println("size: " + size.get(map1));
        System.out.println(table.get(map1) == null);
        System.out.println("######################");
        HashMap<Integer, String> map2 = new HashMap<>(map1);
        System.out.println("thread " + threshold.get(map2));
        System.out.println("负载因子" + loadFactor.get(map2));
        System.out.println("size: " + size.get(map2));
        System.out.println(table.get(map2) == null);


    }
}
