package com.wkk.jdk.collection.list.linkedlist;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @Time: 19-11-18下午1:05
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class Demo {
    public static void main(String[] args) {
        List<String> staff = new LinkedList<String>();
        staff.add("amy");
        staff.add("bob");
        staff.add("carl");
        Iterator iter = staff.iterator();
        String first = (String) iter.next();
        String second = (String) iter.next();
        System.out.println(first);
        System.out.println(second);
        iter.remove();

    }

    @Test
    public void speedGet() {
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            res.add(i);
        }
        List<Long> count = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            long now = System.currentTimeMillis();
            for (Integer re : res) {

            }
            count.add(System.currentTimeMillis() - now);
        }
        double asDouble = count.stream().mapToDouble(i -> i).average().getAsDouble();
        System.out.println(asDouble);
        Iterator<Integer> iterator = res.iterator();
        count.clear();
        for (int i = 0; i < 100; i++) {
            long now = System.currentTimeMillis();
            while (iterator.hasNext()) {
                iterator.next();
            }
            count.add(System.currentTimeMillis() - now);
        }
        asDouble = count.stream().mapToDouble(i->i).average().getAsDouble();
        System.out.println(asDouble);
    }
}
