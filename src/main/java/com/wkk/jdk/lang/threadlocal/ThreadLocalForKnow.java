package com.wkk.jdk.lang.threadlocal;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author weikunkun
 * @since 2021/3/10
 */
public class ThreadLocalForKnow {
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
}
