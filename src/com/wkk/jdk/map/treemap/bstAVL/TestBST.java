package com.wkk.jdk.map.treemap.bstAVL;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @Time: 20-2-25上午11:29
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class TestBST {
    private Random random = new Random();
    private final int MAX1 = 16;

    @Test
    public void testPutAndIt() {
        AVLMap<Integer, String> map = new AVLMap<>();
        for (int i = 0; i < MAX1; i++) {
            map.put(random.nextInt(MAX1), random.nextInt(MAX1) + " ");
        }
        map.checkBalance();
        Iterator<AVLEntry<Integer, String>> iterator = map.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().key + " ");
        }
    }

    @Test
    public void testPutAndItrWithJDK() {
        AVLMap<Integer, String> map1 = new AVLMap<>();
        TreeMap<Integer, String> map2 = new TreeMap<>();
        Random random = new Random();
        int MAX2 = 65535;
        for (int i = 0; i < MAX2; i++) {
            int key = random.nextInt(MAX2);
            String value = random.nextInt(MAX2) + "";
            map1.put(key, value);
            map2.put(key, value);
        }
        Assert.assertTrue(map1.size() == map2.size());

        Iterator<AVLEntry<Integer, String>> it1 = map1.iterator();
        Iterator<Map.Entry<Integer, String>> it2 = map2.entrySet().iterator();
        while (it1.hasNext() && it2.hasNext()) {
            Assert.assertTrue(it1.next().getKey().equals(it2.next().getKey()));
        }
        Assert.assertTrue(!it1.hasNext() && !it2.hasNext());
    }

    @Test
    public void testQuery() {
        AVLMap<Integer, String> map = new AVLMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(4, "d");
        map.put(5, "e");
        map.put(6, "f");
        map.containsValue("f");
        Assert.assertTrue(map.containsKey(1));
        Assert.assertTrue(map.containsValue("f"));
        Assert.assertFalse(map.containsValue("g"));
        Assert.assertTrue(map.get(3).equals("c"));
    }

    @Test
    public void testQueryWithJDK() {
        AVLMap<Integer, String> map1 = new AVLMap<>();
        TreeMap<Integer, String> map2 = new TreeMap<>();
        int max3 = 16;
        for (int i = 0; i < max3; i++) {

        }
    }

    @Test
    public void testRemoveCase1() {
        AVLMap<Integer, String> map = new AVLMap<>();
        int[] arr = {5, 2, 6, 1, 4, 7, 3};
        for (int i : arr) {
            map.put(i, i + "");
        }
        map.remote(1);
        map.levelOrder();
        Iterator<AVLEntry<Integer, String>> it1 = map.iterator();
        while (it1.hasNext()) {
            System.out.print(it1.next().getKey() + " ");
        }
        System.out.println();
    }

    @Test
    public void testRemoveCase2() {
        AVLMap<Integer, String> map = new AVLMap<>();
        int[] arr = {5, 2, 6, 1, 4, 7, 3};
        for (int i : arr) {
            map.put(i, i + "");
        }
        String oldValue = map.remote(4);
        map.levelOrder();
        System.out.println(oldValue);

    }

    @Test
    public void testRemoveCase3(){
        AVLMap<Integer, String> map = new AVLMap<>();
        int[] arr = {5, 2, 6, 1, 4, 7, 3};
        for (int i : arr) {
            map.put(i, i+"");
        }
        map.remote(2);
        map.levelOrder();

    }

    // 泛型测试
    @Test
    public void  test(){
        AVLMap<Person, String> map = new AVLMap<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.id - o2.id;
            }
        });
        int MAX = 16;
        Random random = new Random();
        for (int i = 0; i < MAX; i++) {
            map.put(new Person(random.nextInt(MAX), random.nextInt(MAX) + ""), random.nextInt(MAX) + "");
        }
        Iterator<AVLEntry<Person, String>> itr = map.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next().getKey() + " ");
        }
    }

    @Test
    public void AESCAVLMap(){
        int MAX = 65535;
        AVLMap<Integer, String> map = new AVLMap<>();
        Random random = new Random();
        for (int i = 0; i < MAX; i++) {
            map.put(random.nextInt(MAX), random.nextInt(MAX) + "");
        }
        map.checkBalance();
    }

}
