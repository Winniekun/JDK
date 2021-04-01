package com.wkk.jdk.generic;

/**
 * @author weikunkun
 * @since 2021/3/27
 */
public class Erasure<T extends String> {
    T obj;

    public Erasure(T obj) {
        this.obj = obj;
    }

}
