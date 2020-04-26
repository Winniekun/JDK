package com.wkk.jdk.map.treemap.bstAVL;

import java.util.Map;

/**
 * @Time: 20-2-25上午9:00
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class AVLEntry<K, V> implements Map.Entry<K, V> {
    public K key;
    public V value;
    public AVLEntry<K, V> left;
    public AVLEntry<K, V> right;
    // 将BST ---> AVL
    // 记录AVL的高度
    public int height=1;

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

    public AVLEntry(K key, V value, AVLEntry<K, V> left, AVLEntry<K, V> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public AVLEntry(K key) {
        this.key = key;
    }

    public AVLEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "AVLEntry{ " +
                "key= " + key +
                ", value= " + value
                +", height= " + height
                +" }";
    }
}
