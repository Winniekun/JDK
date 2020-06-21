package com.wkk.jdk.collection.list.copyonwritearryslist;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Time: 2020/6/19下午8:00
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class Test {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7};
        int[] ints = Arrays.copyOf(a, 5);
        System.out.println(ints.hashCode());
        System.out.println(a.hashCode());
        System.out.println(a == ints);
        for (int anInt : ints) {
            System.out.print(anInt+" ");
        }

        System.out.println();
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(1, 5);
        CopyOnWriteArrayList<Integer> li
                = new CopyOnWriteArrayList<>();
        li.add(8);
        li.add(9);
        li.add(10);
        list.addAll(li);
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }


    }
}
