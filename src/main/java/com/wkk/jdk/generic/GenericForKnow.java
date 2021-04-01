package com.wkk.jdk.generic;


import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

/**
 * @author weikunkun
 * @since 2021/3/27
 */
public class GenericForKnow {
    final int a = 100;
    @Test
    public void testClass() {
        GenericClass<String> genericClass = new GenericClass<>("aaa");
        System.out.println(genericClass.getValue());
        People a = new People();
        a.setAge(24);
        a.setName("wkk");
        GenericClass<People> peopleGenericClass = new GenericClass<>(a);
        System.out.println(peopleGenericClass.getValue());
    }

    @Test
    public void testOriginType() {
        Erasure<String> erasure = new Erasure<>("2");
        Class aClass = erasure.getClass();
        System.out.println("erasure class is " + aClass.getName());
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("field name " + field.getName() + " type: " + field.getType().getName());
        }

    }

    @Test
    public void testFinal() throws NoSuchFieldException, IllegalAccessException {
        GenericForKnow know = new GenericForKnow();
        System.out.println(know.a);
        Class<GenericForKnow> knowClass = GenericForKnow.class;
        Field a = knowClass.getDeclaredField("a");
        a.setAccessible(true);
        a.set(know,1001);
        System.out.println(know.a);

    }



}
