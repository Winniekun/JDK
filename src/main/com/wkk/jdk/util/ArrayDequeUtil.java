package com.wkk.jdk.util;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Time: 2020/7/8下午8:45
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class ArrayDequeUtil {
    public static void getField(ArrayDeque list){
        Class aClass = list.getClass();

        try {
            Field elementData = aClass.getDeclaredField("elements");
            // 设为 public
            elementData.setAccessible(true);
            // 获取数组内部元素
            Object[] objects = (Object[]) elementData.get(list);
            Field head = aClass.getDeclaredField("head");
            // 设为public
            head.setAccessible(true);

            Field tail = aClass.getDeclaredField("tail");
            tail.setAccessible(true);
            ArrayList<Integer> arrayList = new ArrayList<>();
            int count = 0;
            for (int i = 0; i < objects.length; i++) {
                if(Objects.nonNull(objects[i])){
                    arrayList.add(i);
                    ++count;
                }
            }
            System.out.println("ArrayDeque length = "+ objects.length
                    + ", 元素的数量 = " + count + ", 对应的位置为 = " + arrayList
                    + ", 头指针在 = " + head.get(list)
                    + ", 尾指针在 = " + tail.get(list));
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
