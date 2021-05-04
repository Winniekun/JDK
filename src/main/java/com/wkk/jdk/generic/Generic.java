package com.wkk.jdk.generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author weikunkun
 * @since 2021/4/14
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Generic<K, V> {
    private K key;
    private V value;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Generic<?, ?> generic = (Generic<?, ?>) o;
        return Objects.equals(key, generic.key) &&
                Objects.equals(value, generic.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

}
