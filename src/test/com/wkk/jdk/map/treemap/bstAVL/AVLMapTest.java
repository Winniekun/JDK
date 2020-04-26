package com.wkk.jdk.map.treemap.bstAVL;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @Time: 2020/4/26下午8:57
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class AVLMapTest {
    private Random random = new Random();
    private final int MAX1 = 16;
    // 迭代功能是否正常
    @Test
    public void testPutAndItr(){
        AVLMap<Integer, String> map=new AVLMap<Integer, String>();
        for(int i=0;i<MAX1;i++){
            map.put(random.nextInt(MAX1), random.nextInt(MAX1)+"");
        }
        Iterator<AVLEntry<Integer, String>> it=map.iterator();
        while(it.hasNext()){
            System.out.print(it.next().key+" ");
        }
        System.out.println();
    }
    private final int MAX2=65535;
    // 和jdk提供的TreeMap的运行结果是否一致
    // 1. 容量
    // 2. 每个键值对是否相等
    @Test
    public void testPutAndItrWithJDK(){
        AVLMap<Integer, String> map1=new AVLMap<Integer, String>();
        TreeMap<Integer, String> map2=new TreeMap<Integer, String>();
        for(int i=0;i<MAX2;i++){
            int key=random.nextInt(MAX2);
            String value=random.nextInt(MAX2)+"";
            map1.put(key, value);
            map2.put(key, value);
        }
        Assert.assertTrue(map1.size()==map2.size());
        Iterator<AVLEntry<Integer, String>> it1=map1.iterator();
        Iterator<Map.Entry<Integer, String>> it2=map2.entrySet().iterator();
        while(it1.hasNext()&&it2.hasNext()){
            Assert.assertTrue(it1.next().getKey().equals(it2.next().getKey()));
        }
        Assert.assertTrue(!it1.hasNext()&&!it2.hasNext());
    }
    // 查测试
    // 主要为：输出是否正确
    @Test
    public void testQuery(){
        AVLMap<Integer, String> map=new AVLMap<Integer, String>();
        map.put(4, "a");
        map.put(2, "b");
        map.put(6, "c");
        map.put(2, "d");
        map.put(1, "a");
        map.put(3, "d");
        map.put(5, "e");
        map.put(1, "f");
        Assert.assertTrue(map.get(4).equals("a"));
        Assert.assertTrue(map.get(1).equals("f"));
        Assert.assertTrue(map.get(7)==null);
        Assert.assertTrue(map.containsKey(6));
        Assert.assertTrue(!map.containsKey(-1));
        Assert.assertTrue(map.containsValue("d"));
        Assert.assertTrue(!map.containsValue("xxxx"));
    }
    @Test
    // 查和TreeMap的是否相同
    public void testQUeryWithJDK(){
        AVLMap<Integer, String> map1=new AVLMap<Integer, String>();
        TreeMap<Integer, String> map2=new TreeMap<Integer, String>();
        for(int i=0;i<MAX2;i++){
            int key=random.nextInt(MAX2);
            String value=random.nextInt(MAX2)+"";
            map1.put(key, value);
            map2.put(key, value);
        }
        for(int i=0;i<MAX2;i++){
            int key=random.nextInt(MAX2);
            Assert.assertTrue(map1.containsKey(key)==map2.containsKey(key));
            if(map1.get(key)==null){
                Assert.assertTrue(map2.get(key)==null);
            }else{
                Assert.assertTrue(map1.get(key).equals(map2.get(key)));
            }
        }
        for(int i=0;i<255;i++){
            String value=random.nextInt(MAX2)+"";
            Assert.assertTrue(map1.containsValue(value)==map2.containsValue(value));
        }
    }
    // 删除的测试3种情况
    // 1. 当前节点为叶子节点
    @Test
    public void testRemoveCase1(){
        AVLMap<Integer, String> map=new AVLMap<Integer, String>();
        int[] array={5,2,6,1,4,7,3};
        for(int key:array){
            map.put(key, key+"");
        }
        System.out.println(map.remove(1));
        map.levelOrder();
        Iterator<AVLEntry<Integer, String>> it=map.iterator();
        while(it.hasNext()){
            System.out.print(it.next().key+" ");
        }
        System.out.println();
    }
    // 2. 当前节点只有左子树或右子树
    @Test
    public void testRemoveCase2(){
        AVLMap<Integer, String> map=new AVLMap<Integer, String>();
        int[] array={5,2,6,1,4,7,3};
        for(int key:array){
            map.put(key, key+"");
        }
        System.out.println(map.remove(4));
        map.levelOrder();
        Iterator<AVLEntry<Integer, String>> it=map.iterator();
        while(it.hasNext()){
            System.out.print(it.next().key+" ");
        }
        System.out.println();
    }
    // 3. P既有左子树又有右子树
    @Test
    public void testRemoveCase3(){
        AVLMap<Integer, String> map=new AVLMap<Integer, String>();
        int[] array={6,2,7,1,4,8,3,5};
        for(int key:array){
            map.put(key, key+"");
        }
        System.out.println(map.remove(2));
        map.levelOrder();
        Iterator<AVLEntry<Integer, String>> it=map.iterator();
        while(it.hasNext()){
            System.out.print(it.next().key+" ");
        }
        System.out.println();
    }
    // 删除和TreeMap是否相同
    @Test
    public void testRemoveWithJDK(){
        AVLMap<Integer, String> map1=new AVLMap<Integer, String>();
        TreeMap<Integer, String> map2=new TreeMap<Integer, String>();
        for(int i=0;i<MAX2;i++){
            int key=random.nextInt(MAX2);
            String value=random.nextInt(MAX2)+"";
            map1.put(key, value);
            map2.put(key, value);
        }
        System.out.println(map1.size());
        for(int i=0;i<MAX2>>>1;i++){
            int key=random.nextInt(MAX2);
            if(map1.containsKey(key)){
                Assert.assertTrue(map1.remove(key).equals(map2.remove(key)));
            }else{
                Assert.assertTrue(map1.remove(key)==null&&map2.remove(key)==null);
            }
        }
        System.out.println(map1.size());
        Assert.assertTrue(map1.size()==map2.size());
        Iterator<AVLEntry<Integer, String>> it1=map1.iterator();
        Iterator<Map.Entry<Integer, String>> it2=map2.entrySet().iterator();
        while(it1.hasNext()&&it2.hasNext()){
            Assert.assertTrue(it1.next().getKey().equals(it2.next().getKey()));
        }
        Assert.assertTrue(!it1.hasNext()&&!it2.hasNext());
    }
    // 泛型测试
    @Test
    public void testPerson(){
        AVLMap<Person, Integer> map=new AVLMap<Person, Integer>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.id-o1.id;
            }
        });
        for(int i=0;i<MAX1;i++){
            map.put(new Person(random.nextInt(MAX1), "name"+random.nextInt(MAX1)), random.nextInt(MAX2));
        }
        Iterator<AVLEntry<Person, Integer>> itr=map.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next().getKey());
        }
    }

}
