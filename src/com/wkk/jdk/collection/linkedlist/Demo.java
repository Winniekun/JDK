package com.wkk.jdk.collection.linkedlist;

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
}
