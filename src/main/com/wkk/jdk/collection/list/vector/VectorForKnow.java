package com.wkk.jdk.collection.list.vector;

import java.util.Stack;

/**
 * @Time: 2020/5/26下午5:59
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class VectorForKnow {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.search(1));
    }
}
