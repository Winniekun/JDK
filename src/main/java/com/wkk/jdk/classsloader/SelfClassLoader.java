package com.wkk.jdk.classsloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * 类加载器
 *
 * @author weikunkun
 * @since 2021/4/3
 */
public class SelfClassLoader extends ClassLoader {
    private String root;

    /**
     * 指定这个类加载器可以加载的类的根路径
     */
    public SelfClassLoader(String root) {
        super(null);
        this.root = root;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file;
        // 将类名转换为path
        String path = root + name.replace('.', '/').concat(".class");
        file = new File(path);
        if (!file.exists()) {
            throw new ClassNotFoundException(name);
        }
        Class<?> clazz;
        try {
            // 读取字节码并调用defineClass方法加载类
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            int result = inputStream.read(bytes);
            clazz = defineClass(null, bytes, 0, result);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }
        return clazz;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}
