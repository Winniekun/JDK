package com.wkk.jdk.collection.arraylist;

import java.util.Arrays;

/**
 * @Time: 19-11-8下午9:30
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class SorceForKnow {
    public static void main(String[] args) {
        int a  = 100;
        System.out.println(a >> 1);
        System.out.println(Integer.MAX_VALUE - 8);
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = Arrays.copyOf(arr1, 2);
        int[] arr3 = Arrays.copyOf(arr1, 10);
        for(int b : arr2){
            System.out.print(b + "");
        }

        for(int b: arr3){
            System.out.print(b+ "");
        }

        System.out.println("========================");
        int[] c = new int[10];
        c[0] = 0;
        c[1] = 1;
        c[2] = 2;
        c[3] = 3;
        System.arraycopy(c, 2, c, 3, 3);
        c[2] = 99;
        for (int i : c) {
            System.out.print(i+" ");
        }

        System.out.println("========================");
        int[] d = new int[3];
        d[0] = 0;
        d[1] = 1;
        d[2] = 3;
        int[] e = Arrays.copyOf(d, 10);
        System.out.println("e.lenght="+e.length);
    }

}
