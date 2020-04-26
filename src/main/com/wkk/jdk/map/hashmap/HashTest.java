package com.wkk.jdk.map.hashmap;

import java.util.HashSet;
import java.util.Set;

/**
 * @Time: 19-12-4下午7:06
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class HashTest {
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int hasCode(){
        return i%10;
    }


    public static void main(String[] args) {
        HashTest hashTest = new HashTest();
        HashTest b = new HashTest();
        hashTest.setI(1);
        b.setI(1);
        Set<HashTest> set = new HashSet<HashTest>();
        set.add(hashTest);
        set.add(b);
        System.out.println(hashTest.hasCode() == b.hasCode());
        System.out.println(hashTest.equals(b));
        System.out.println(set);
    }
}
