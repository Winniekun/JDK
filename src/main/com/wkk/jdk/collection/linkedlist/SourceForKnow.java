package com.wkk.jdk.collection.linkedlist;

import java.util.LinkedList;

/**
 * @Time: 20-1-5下午4:17
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class SourceForKnow {
    public static void main(String[] args) {
        LinkedList<String> ll = new LinkedList<>();
        ll.add("我");
        ll.add("是");
        ll.add("孔维坤");
        System.out.println("当前的LinkedList为：" + ll);
//        LinkedList<String> newList = new LinkedList<>(ll);
//        System.out.println("新的list为： " + newList);
        LinkedList<String> aa = new LinkedList<>();
        aa.add("我");
        aa.add("是");
        aa.add("韩欢欢");
        System.out.println("当前的LinkedList为：" + aa);
        System.out.println("addAll()? " + aa.addAll(ll));
        System.out.println("使用addAll()之后的链表W为: " + aa);
        System.out.println();
        System.out.println();
//        List list = new LinkedList();
//        list.add(11);
//        list.add(0);
//        System.out.println(list.size());
//        if (list.size() < 0) {
//            throw new IndexOutOfBoundsException("size<0");
//        } else {
//            throw new IndexOutOfBoundsException("size() > 0");
//        }
        LinkedList<Integer> linkedList = new LinkedList<>();
        LinkedList<Integer> temp = new LinkedList<>();
        System.out.println(temp.addAll(linkedList));

        LinkedList<Integer> linkedList1 = new LinkedList<>();
        linkedList1.add(1);
        linkedList1.add(2);
        linkedList1.add(3);
        linkedList1.add(1, 4);
        System.out.println("linkedlist: " + linkedList1);

        System.out.println("set(3, 5): " + linkedList1.set(3, 5));
        System.out.println("After set(3, 5)" + linkedList1);


    }
}
