package com.wkk.jdk.collection.list.arraylist;

import java.util.*;

/**
 * @Time: 19-10-15下午4:17
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class ArrayLIstTest {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        System.out.printf("Before add: arrayList.size()=%d\n", arrayList.size());

        arrayList.add(1);
        arrayList.add(3);
        arrayList.add(5);
        arrayList.add(7);
        arrayList.add(9);

        System.out.printf("After ad: arrayList.size()=%d\n", arrayList.size());

        System.out.println("Printing elemnents of arrayList");
        // 三种方式进行遍历
        // 第一种: 迭代器遍历
        System.out.print("通过迭代器遍历");
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext()){
            System.out.print(it.next() + " ");
        }

        System.out.println();

        // 第二种: 通过索引值遍历
        System.out.println("通过索引值遍历");
        for(int i = 0; i< arrayList.size(); i++){
            System.out.print(arrayList.get(i) + " ");
        }

        System.out.println();

        // 第三种: for-each
        System.out.println("for-each循环");
        for(int i : arrayList){
            System.out.print(i + " ");
        }

        //#########################
        // toArray方法
        //#########################

        // 第一种: 最常用方法
        Integer[] integers = arrayList.toArray(new Integer[0]);
        System.out.println("最常用的toArray方法");

        for(int c : integers){
            System.out.print(c + " ");
        }
        System.out.println();
        // 第二种: 易于理解
        Integer[] integers1 = new Integer[arrayList.size()];

        // 指定位置添加元素
        arrayList.add(2, 100);
        // 指定位置删除元素
        arrayList.remove(2);
        // 删除指定元素
        arrayList.remove((Object)3);

        // 判断是否包含有某元素
        System.out.println("Arraylist contains 5 is: " + arrayList.contains(5));

        // 清空ArrayList
        arrayList.clear();
        // 判断是否为空
        System.out.println("Arraylist is empty: "+ arrayList.isEmpty());


        ArrayList<Object> list = new ArrayList<>();
        final int N = 10000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapaticy方法之前: "+ (endTime - startTime));

        list = new ArrayList<>();
        list.ensureCapacity(N);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);

        }

        endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapaticy方法之后: "+ (endTime -startTime));

        ArrayList<Integer> list3= new ArrayList<>();
        list.add(1);

    }
}
