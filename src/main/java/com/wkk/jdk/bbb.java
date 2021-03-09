package com.wkk.jdk;

/**
 * @Time: 2020/5/24下午1:12
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public abstract class bbb implements aa {
    public void test() {
        System.out.println("fdsaa");
    }

    @Override
    public boolean add() {
        return false;
    }

    @Override
    public int removeAll() {
        return 0;
    }
    public void say(){
        System.out.println("我是父类");
    }
}
