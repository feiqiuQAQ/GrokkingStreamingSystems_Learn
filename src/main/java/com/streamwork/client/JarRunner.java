package com.streamwork.client;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class JarRunner {

    public static void main(String[] args) { 
        if (args.length < 2) {
            System.err.println("Usage: JarRunner <jarFilePath> <mainClassName> [args...]");
            System.exit(1);
        }

        String jarFilePath = args[0]; // 用户提交的 JAR 包路径
        String mainClassName = args[1]; // q用户程序的入口类
        String[] programArgs = new String[args.length - 2]; // 用户程序的参数
        System.arraycopy(args, 2, programArgs, 0, programArgs.length);

        try {
            // 加载 JAR 包
            URL jarUrl = new File(jarFilePath).toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});

            // 加载用户程序的入口类
            Class<?> mainClass = classLoader.loadClass(mainClassName);

            // 获取 main 方法
            Method mainMethod = mainClass.getMethod("main", String[].class);

            // 调用 main 方法
            System.out.println("Running program from JAR: " + jarFilePath);
            mainMethod.invoke(null, (Object) programArgs);

            // 关闭类加载器
            classLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}