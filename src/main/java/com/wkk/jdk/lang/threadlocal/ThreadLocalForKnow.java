package com.wkk.jdk.lang.threadlocal;

import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author weikunkun
 * @since 2021/3/10
 */
public class ThreadLocalForKnow {
    private static final int HASH_INCREMENT = 0x61c88647;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(()->new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Test
    public void test() {
        int count = 0;
        while (count < 1000) {
            new Thread(() ->{
//                String data = format.format(new Date());
                String dataStr = threadLocal.get().format(new Date());
                try {
                    Date parseDate = ThreadLocalForKnow.format.parse(dataStr);
                    String formatDateStr = ThreadLocalForKnow.format.format(parseDate);
                    boolean equals = dataStr.equals(formatDateStr);
                    if (equals) {
                        System.out.println(equals);
                    } else  {
                        System.out.println(equals + " " + dataStr + " " + formatDateStr);
                    }
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }
            }).start();
            count++;
        }
    }

    @Test
    public void testHash() {
        int hashCode = 0;
        for (int i = 0; i < 16; i++) {
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            int idx = hashCode & 15;
            System.out.println("斐波那契散列：" + idx + " 普通散列: " + (String.valueOf(i).hashCode() & 15));
        }

    }

    @Test
    public void testEveryThreadLocalHash() throws Exception {
        for (int i = 0; i < 5; i++) {
            ThreadLocal threadLocal = new ThreadLocal();
            Field threadLocalHashCode = threadLocal.getClass().getDeclaredField("threadLocalHashCode");
            threadLocalHashCode.setAccessible(true);
            System.out.println("current threadLocalHashCode: " + threadLocalHashCode.get(threadLocal));
        }
    }
}
