package com.wkk.jdk.generic;

/**
 * @author weikunkun
 * @since 2021/3/27
 */
public class GenericClass<T> {
    private T filed;

    public GenericClass(T filed) {
        this.filed = filed;
    }

    public T getValue() {
        return filed;
    }
}
