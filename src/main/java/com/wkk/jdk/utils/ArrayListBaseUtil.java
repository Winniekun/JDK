package com.wkk.jdk.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Time: 2020/5/24下午4:44
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class ArrayListBaseUtil {
    // 没有节操的反射
    public static void getField(ArrayList list){
        Class aClass = list.getClass();

        try {
            Field elementData = aClass.getDeclaredField("elementData");
            // 设为 public
            elementData.setAccessible(true);
            // 获取数组内部元素
            Object[] objects = (Object[]) elementData.get(list);
            Field size = aClass.getDeclaredField("size");
            // 设为public
            size.setAccessible(true);

            int count = 0;
            for (int i = 0; i < objects.length; i++) {
                if(Objects.nonNull(objects[i])){
                    ++count;
                }
            }
            System.out.println("ArrayList中 length = "+ objects.length
            + ", 元素的数量 = " + size.get(list)
            + ", 数组大小 = " + count);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
