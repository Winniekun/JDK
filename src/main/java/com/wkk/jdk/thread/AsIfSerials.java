package com.wkk.jdk.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author weikunkun
 * @since 2021/3/22
 */
@Slf4j
public class AsIfSerials {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    int fsafsf = Integer.parseInt("fsafsf");
                } catch (Exception e) {
                    log.info("error");
                }

            }
        });
        executorService.shutdown();
    }
}
