package com.wkk.jdk.thread.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author weikunkun
 * @since 2021/5/12
 */
@Slf4j
public class Demo {
    private final static Random R = new Random();

    public static class SearchTask implements Runnable {
        private int id;
        private CountDownLatch latch;

        public SearchTask(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println("开始寻找" + id + "号龙珠");
            int seconds = R.nextInt(10);
            long now = System.currentTimeMillis();
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println("消耗 " + (end - now) / 1000 + "s, 找到了" + id + "号龙珠");
            latch.countDown();
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        CountDownLatch latch = new CountDownLatch(list.size());
        for (Integer id : list) {
            Thread thread = new Thread(new SearchTask(id, latch));
            thread.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.info("error happened ", e);
        }
        System.out.println("所有龙珠找到，召唤神龙");
    }
}
