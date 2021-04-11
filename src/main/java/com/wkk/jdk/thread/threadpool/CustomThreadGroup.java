package com.wkk.jdk.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weikunkun
 * @since 2021/4/11
 */
@Slf4j
public class CustomThreadGroup extends ThreadGroup{

    public CustomThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        log.error("CustomThreadPoolExecutor execute Exception: {}",
                ThreadUtils.stackTrace(e.getStackTrace()));
    }
}
