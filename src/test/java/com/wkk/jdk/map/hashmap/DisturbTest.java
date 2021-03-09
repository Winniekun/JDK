package com.wkk.jdk.map.hashmap;

import com.wkk.jdk.utils.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author weikunkun
 * @since 2021/3/9
 */
public class DisturbTest {

    private Set<String> words;

    @Before
    public void before() {
        // 读取文件，103976个英语单词库.txt
        words = FileUtil.readWordList("src/main/resources/103976个英语单词库.txt");
    }

    @Test
    public void test_disturb() {
        Map<Integer, Integer> map = new HashMap<>(words.size());
        for (String word : words) {
            // 使用扰动函数
            int idx = Disturb.disturbHashIdx(word, 128);
            // 不使用扰动函数
            // int idx = Disturb.hashIdx(word, 128);
            map.put(idx, map.getOrDefault(idx, 0) + 1);
        }
        System.out.println(map);
    }
}