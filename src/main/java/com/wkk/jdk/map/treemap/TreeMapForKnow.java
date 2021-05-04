package com.wkk.jdk.map.treemap;

import com.wkk.jdk.generic.People;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.time.Period;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author weikunkun
 * @since 2021/3/31
 */
public class TreeMapForKnow {
    @Test
    public void testAdd() {
        Map<Integer, Integer> map = new TreeMap<>();
        map.put(1000, 1);
        map.put(1, 11);
        map.put(2, 1);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    @Test
    public void testSort() {
        TreeMap<Person, String> map = new TreeMap<>();
        Person person1 = new Person("aaa");
        Person person2 = new Person("bbb");
        Person person3 = new Person("ccc");
        map.put(person1,"111");
        map.put(person2,"222");
        map.put(person3,"333");
        System.out.println(map);

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Person implements Comparable<Person> {
        private String name;

        @Override
        public int compareTo(Person o) {
            return this.getName().compareTo(o.getName());
        }
    }
}
