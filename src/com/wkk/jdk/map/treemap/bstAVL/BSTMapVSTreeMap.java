package com.wkk.jdk.map.treemap.bstAVL;

import org.junit.Test;

import java.util.Random;
import java.util.TreeMap;

/**
 * @Time: 20-2-26下午8:55
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class BSTMapVSTreeMap {
    public static final int MAX = 20480;
    private Random random = new Random();

    @Test
    public void testRandomTreeMap() {
        TreeMap<Integer, String > map = new TreeMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(random.nextInt(MAX), random.nextInt(MAX) + "");
        }
        for (int i = 0; i < MAX; i++) {
            map.get(random.nextInt(MAX));
        }
    }
    @Test
    public void testRandomAVLMap(){
        AVLMap<Integer, String> map = new AVLMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(random.nextInt(MAX), random.nextInt(MAX) + "");
        }
        for (int i = 0; i < MAX; i++) {
            map.get(random.nextInt(MAX));
        }
    }
    @Test
    public void testAESCTreeMap(){
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(i, i+"");
        }
        for (int i = 0; i < MAX; i++) {
            map.get(i);
        }

    }
    @Test
    public void testAESCAVLMap(){
        AVLMap<Integer, String> map = new AVLMap<>();
        for (int i = 0; i < MAX; i++) {
            map.put(i, i+"");
        }
        for (int i = 0; i < MAX; i++) {
            map.get(i);
        }

    }
}
