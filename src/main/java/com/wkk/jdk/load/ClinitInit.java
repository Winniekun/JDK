package com.wkk.jdk.load;

/**
 * @author weikunkun
 * @since 2021/4/3
 */
public class ClinitInit {
    public static ClinitInit test;

    static {
        System.out.println("static 开始");
        // 下面这句编译器报错，非法向前引用
        // System.out.println("x=" + x);
        test = new ClinitInit();
        System.out.println("static 结束");

    }

    public ClinitInit() {
        System.out.println("构造 开始");
        System.out.println("x=" + x + ";y=" + y);
        // 构造器可以访问声明于他们后面的静态变量
        // 因为静态变量在类加载的准备阶段就已经分配内存并初始化0值了
        // 此时 x=0，y=0
        x++;
        y++;
        System.out.println("x=" + x + ";y=" + y);
        System.out.println("构造器结束");
    }

    public static ClinitInit getInstance() {
        return test;
    }

    public static int x = 6;
    public static int y = 9;

    public static void main(String[] args) {
        ClinitInit obj = ClinitInit.getInstance();
        System.out.println("x=" + obj.x);
        System.out.println("y=" + obj.y);
    }
}
