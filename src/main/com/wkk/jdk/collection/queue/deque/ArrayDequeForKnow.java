package com.wkk.jdk.collection.queue.deque;

import com.wkk.jdk.util.ArrayDequeUtil;
import org.junit.Test;

import java.util.*;

/**
 * @Time: 2020/7/8下午3:19
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class ArrayDequeForKnow {

    @Test
    public void reflect(){
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>(6);
        ArrayDequeUtil.getField(arrayDeque);
        for (int i = 0; i < 10; i++) {
            arrayDeque.addFirst(i);
            ArrayDequeUtil.getField(arrayDeque);
        }
        arrayDeque.pollLast();
        ArrayDequeUtil.getField(arrayDeque);


    }

    @Test
    public void testBase(){
//        Deque<Integer> deque = new ArrayDeque<>();
//        Queue<Integer> queue = new ArrayDeque<>();
//        deque.add(1);
//        deque.add(2);
//        deque.add(3);
//        deque.add(4);
//
//        deque.offer(6);
//        deque.offerFirst(0);
//        deque.offerLast(9);
//        // 迭代
//        Iterator<Integer> iterator = deque.iterator();
//        while (iterator.hasNext()){
//            System.out.print(iterator.next() + " ");
//        }
//        System.out.println();
//        System.out.println("pop()");
//        deque.pop();
//        System.out.println(deque);
//
//        System.out.println("pollFirst()");
//        deque.pollFirst();
//        System.out.println(deque);
//
//        System.out.println("pollLast()");
//        deque.pollLast();
//        System.out.println(deque);

        // 理论上和HashMap中的指定初始化容量大小的最后数组容量定容类似
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>(6);
        for (int i = 0; i < 10; i++) {
            arrayDeque.addFirst(i);
        }




//        int tail = 0;
//        int[] elements = new int[16];
//        for (int i = 0; i < elements.length; i++) {
//            tail = (tail + 1) & (elements.length - 1);
//            System.out.println("第" + (i+1) + "次计算，结果值：" + tail);
//        }
    }
}
