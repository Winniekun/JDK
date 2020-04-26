package com.wkk.jdk.map.treemap.bstAVL;

import org.junit.Test;

import java.util.Random;
import java.util.TreeMap;

/**
 * @Time: 2020/4/26下午8:58
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class AVLPerformed {
    public static final int MAX=65535;
    private Random random=new Random();
    // 用于测试自己编写的AVLMap和TreeMap的性能区别
    // 主要是随机65535条数据，查看运行时间
    // 升序插入65536条数据，查看运行时间
    @Test
    public void testBSTRandom(){
        AVLMap<Integer, String> map=new AVLMap<Integer, String>();
        for(int i=0;i<MAX;i++){
            map.put(random.nextInt(MAX), random.nextInt(MAX)+"");
        }
        map.checkBalance();
        for(int i=0;i<MAX;i++){
            map.get(random.nextInt(MAX));
        }
    }
    @Test
    public void testTreeMapRandom(){
        TreeMap<Integer, String> map=new TreeMap<Integer, String>();
        for(int i=0;i<MAX;i++){
            map.put(random.nextInt(MAX), random.nextInt(MAX)+"");
        }
        for(int i=0;i<MAX;i++){
            map.get(random.nextInt(MAX));
        }
    }
    @Test
    //
    public void testBSTIncrement(){
        AVLMap<Integer, String> map=new AVLMap<Integer, String>();
        for(int i=0;i<MAX;i++){
            map.put(i, random.nextInt(MAX)+"");
        }
        map.checkBalance();
        for(int i=0;i<MAX;i++){
            map.get(random.nextInt(MAX));
        }
    }
    @Test
    public void testTreeMapIncrement(){
        TreeMap<Integer, String> map=new TreeMap<Integer, String>();
        for(int i=0;i<MAX;i++){
            map.put(i, random.nextInt(MAX)+"");
        }
        for(int i=0;i<MAX;i++){
            map.get(random.nextInt(MAX));
        }

    }
}
