package com.wkk.jdk.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @author weikunkun
 * @since 2021/3/9
 */
public class FileUtil {

    /**
     * 读取文件
     * @param url
     * @return
     */
    public static Set<String> readWordList(String url) {
        Set<String> list = new HashSet<>();
        try ( InputStreamReader isr = new InputStreamReader(new FileInputStream(url), StandardCharsets.UTF_8);
              BufferedReader br = new BufferedReader(isr);
              ){
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] ss = line.split("\t");
                list.add(ss[1]);
            }
        } catch (Exception ignore) {
            return null;
        }
        return list;
    }
}
