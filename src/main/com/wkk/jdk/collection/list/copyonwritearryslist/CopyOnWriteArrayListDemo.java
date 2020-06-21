package com.wkk.jdk.collection.list.copyonwritearryslist;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Time: 2020/6/19下午5:26
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("wkk");
        vector.add("kwk");
        vector.add("kww");

        new Thread(()->getLast(vector)).start();
        new Thread(()->deleteLast(vector)).start();
        new Thread(()->getLast(vector)).start();
        new Thread(()->deleteLast(vector)).start();
        new Thread(()->getLast(vector)).start();
        new Thread(()->deleteLast(vector)).start();


    }

    // 得到Vector最后一个元素
    public static Object getLast(Vector list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    // 删除Vector最后一个元素
    public static void deleteLast(Vector list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }
}

class HelloThread implements Runnable{
    private static CopyOnWriteArrayList list = new CopyOnWriteArrayList();
    static {
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
    }
    @Override
    public void run() {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            list.add("AA");
        }
    }
}
