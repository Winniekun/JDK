package com.wkk.jdk.map.treemap.rbTree;

import com.wkk.jdk.map.treemap.bstAVL.AVLMap;
import org.junit.Test;

import java.util.Random;
import java.util.TreeMap;

/**
 * @Time: 20-2-27下午3:54
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class TestRBT {
    private static AVLMap<Integer, Integer> avlMap = new AVLMap<>();
    private static TreeMap<Integer, Integer> rbMap = new TreeMap<>();
    private Random random = new Random();
    private int MAX = (1<<21) + 9999;


    @Test
    public void testPrint() throws Exception {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(2,2);
        map.put(4,4);
        map.put(1,1);
        map.put(5,5);
        map.put(6,6);
        map.put(3,3);
        ReflectUtilForTreeMap.levelOrderPrintTree(map);
        System.out.println(new Random().nextInt(12344));

    }

    @Test
    public void TestAVLInsert(){
        for (int i = 0; i < MAX; i++) {
            avlMap.put(random.nextInt(MAX), random.nextInt(MAX));
        }
    }

    @Test
    public void TestRBInsert(){
        for (int i = 0; i < MAX; i++) {
            rbMap.put(random.nextInt(MAX), random.nextInt(MAX));
        }
    }

    @Test
    public void TestContainAVL(){
        for (int i = 0; i < MAX; i++) {
            avlMap.containsKey(random.nextInt(MAX));
        }
    }

    @Test
    public void TestContainRB(){
        for (int i = 0; i < MAX; i++) {
            rbMap.containsKey(random.nextInt(MAX));
        }
    }


}
