package com.wkk.jdk.collection.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author weikunkun
 * @since 2021/3/9
 */
public class ListAna {
    private Integer max = 10000000;
    @Test
    public void test_add_first_arrayList_linkedList() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        long start = System.currentTimeMillis();
        for (Integer i = 0; i < max; i++) {
            arrayList.add(0, i);
        }
        System.out.println("array list use :" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (Integer i = 0; i < max; i++) {
            linkedList.addFirst(i);
        }
        System.out.println("linked list use :" + (System.currentTimeMillis() - start));
    }
}