package com.wkk.jdk.map.treemap.bstAVL;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * @Time: 20-2-25上午11:11
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class AVLIterator<K, V> implements Iterator<AVLEntry<K,V>> {
    private Stack<AVLEntry<K, V>> stack;

    public AVLIterator(AVLEntry<K, V> root){
        stack = new Stack<>();
        addLeft(root);
    }
    private void addLeft(AVLEntry<K, V> p){
        while (p != null){
            stack.push(p);
            p = p.left;
        }
    }

    @Override
    public boolean hasNext() {
        return stack.isEmpty()?false:true;
    }

    @Override
    public AVLEntry<K, V> next() {
        AVLEntry<K, V> top = stack.pop();
        addLeft(top.right);
        return top;
    }

    @Override
    public void remove() {
        throw  new ConcurrentModificationException("can not remove");
    }
}
