package com.wkk.jdk.collection.list.linkedlist;


import java.util.Iterator;
import java.util.LinkedList;

/**
 * @Time: 20-2-1下午6:26
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class LinkedListTest {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addFirst(0); // 在头部添加元素
        linkedList.add(1); // 在尾部添加元素
        linkedList.add(2, 2); //在指定位置添加元素
        linkedList.addLast(3); // 添加元素在列表结尾
        linkedList.add(2);
        linkedList.addLast(3);

        System.out.println("#####################################");
        System.out.println("linkedlist直接输出: " + linkedList);
        System.out.println("获取头部元素: " + linkedList.getFirst());
        System.out.println("获取尾部元素: " + linkedList.getLast());
        System.out.println("删除头部元素: " + linkedList.removeFirst());
        System.out.println("删除尾部元素: " + linkedList.removeLast());
        System.out.println("删除索引位置为2的元素: " + linkedList.remove(2));
        System.out.println("处理之后的list: " + linkedList);
        System.out.println("contains(1)方法判断: " + linkedList.contains(1));
        System.out.println("linkedList大小: " + linkedList.size());

        System.out.println("##################位置访问操作################");
        linkedList.set(1, 10); //将此位置的更改为3
        System.out.println("set(1, 3)后的linklist: " + linkedList); // set(1, 3)
        System.out.println("get(2   )后的linklist: " + linkedList.get(1));// get(2)

        System.out.println("###############search操作###################");
        linkedList.add(19);
        linkedList.addFirst(10);
        System.out.println("linkedlist: " + linkedList);
        System.out.println("indexOf(19): " + linkedList.indexOf(19));
        System.out.println("indexOf(10): " + linkedList.indexOf(10));
        System.out.println("lastIndexOf(10): " + linkedList.lastIndexOf(10));

        System.out.println("###############queue操作###################");
        System.out.println(linkedList);
        // 获取元素 但是不移除此列表的头
        System.out.println("peek(): " + linkedList.peek());
        System.out.println("element():" + linkedList.element());
        // 获取并移除列表头
        linkedList.poll();
        System.out.println("After poll()" + linkedList);
        linkedList.remove();
        System.out.println("After remove()" + linkedList);
        linkedList.offer(199);
        // 将指定元素添加到列表末尾
        System.out.println("After offer(4)" + linkedList.offer(4));

        System.out.println("###############deque操作###################");
        linkedList.offerFirst(2);
        System.out.println("After offerFirst(2) " + linkedList);
        System.out.println("peekFirst() " + linkedList.peekFirst());
        System.out.println("peekLast()" + linkedList.peekLast());
        // 获取并移除第一个元素
        linkedList.pollFirst();
        System.out.println("After pollFirst()" + linkedList);
        linkedList.pollLast();
        System.out.println("After pollLast()" + linkedList);
        linkedList.push(102);
        System.out.println("After push: " + linkedList);
        linkedList.pop();
        System.out.println("After pop: " + linkedList);
        linkedList.add(102);
        System.out.println("当前的linkedlist: " + linkedList);
        linkedList.removeFirstOccurrence(102);
        System.out.println("After removeFirstOccurrence: "+ linkedList);

        System.out.println("###############遍历操作###################");
        linkedList.clear(); // 清空元素
        for(int i =0; i< 10000; i++){
            linkedList.add(i);
        }
        // 迭代遍历
        System.out.println("###############迭代器遍历#####################");
        long start = System.currentTimeMillis();
        Iterator<Integer> iterator = linkedList.iterator();
        while (iterator.hasNext()){
            iterator.next();
        }
        long end = System.currentTimeMillis();
        System.out.println("consume " + (end-start) + "ms");

        // 顺序遍历
        start = System.currentTimeMillis();
        for (Integer integer : linkedList) {
            ;
        }
        System.out.println("consume: "+ (System.currentTimeMillis() - start) + "ms");

        // 使用遍历并删除的方式遍历元素
        LinkedList<Integer> temp = new LinkedList<>();
        temp.addAll(linkedList);
        start = System.currentTimeMillis();
        while (!temp.isEmpty()){
            temp.pollFirst();
        }
        end = System.currentTimeMillis();
        System.out.println("consume: "+(end-start) + "ms");

        System.out.println("是否为空: " + temp.isEmpty());

        LinkedList<Integer> temp2 = new LinkedList<>();
        temp2.addAll(linkedList);
        start = System.currentTimeMillis();
        for (Integer integer : temp) {
            temp2.removeFirst();
        }
        System.out.println("consume: " + (System.currentTimeMillis()-start) + "ms");


    }
}
