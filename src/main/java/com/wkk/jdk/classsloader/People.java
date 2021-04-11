package com.wkk.jdk.classsloader;

import java.lang.reflect.Field;

/**
 * @author weikunkun
 * @since 2021/4/3
 */
public class People {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) throws Exception {
        SelfClassLoader selfClassLoader = new SelfClassLoader("/Users/weikunkun/IdeaProjects/JDK/src/main/java/com/wkk/jdk/classsloader/SelfClassLoader.java");
        // 加载该路径下的People这个类
        Class<?> clazzCustomized = selfClassLoader.loadClass("People");
        Object instanceCustomized = clazzCustomized.newInstance();
        Field field1 = clazzCustomized.getField("name");
        Field field2 = clazzCustomized.getField("age");
        field1.set(instanceCustomized, "mike");
        field2.set(instanceCustomized, 18);
        System.out.println("name is " + field1.get(instanceCustomized));
        System.out.println("age is " + field2.get(instanceCustomized));
    }
}
