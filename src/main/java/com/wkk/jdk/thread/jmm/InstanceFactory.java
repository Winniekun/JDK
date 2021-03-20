package com.wkk.jdk.thread.jmm;

/**
 * JVM在类的初始化阶段（class被加在后，且被线程使用之前）会执行类的初始化
 * 执行类的初始化期间，JVM会去获取一个锁，锁会同步多个线程对同一个类的初始化
 * @author weikunkun
 * @since 2021/3/20
 */
public class InstanceFactory {
    private static class InstanceHolder {
        public static Object object = new Object();
    }

    public static Object getInstance() {
        return InstanceHolder.object;
    }
}
