package com.wkk.jdk.collection.list.arraylist;

import com.wkk.jdk.util.ArrayListBaseUtil;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @Time: 2020/5/24下午5:08
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class ArrayLIstTestTest {
    @Test
    public void testAddAtTail(){

        int initCapacity = 8;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < initCapacity * 6; i++) {
            ArrayListBaseUtil.getField(list);
            list.add(0, i);
        }

    }
}
