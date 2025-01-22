package com.streamwork.ch02.client;

import com.streamwork.ch02.api.Job;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class FrameworkRunner {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FrameworkRunner <path-to-jar>");
            return;
        }

        String jarPath = args[0];
        try {
            // 加载用户的 JAR 文件
            File jarFile = new File(jarPath);
            if (!jarFile.exists()) {
                System.out.println("JAR file does not exist: " + jarPath);
                return;
            }

            URL[] urls = {jarFile.toURI().toURL()};
            URLClassLoader classLoader = new URLClassLoader(urls);

            // 动态加载 JAR 包中的类
            String className = "com.example.JobImpl";  // 假设用户的 JAR 包中有一个实现了 Job 接口的类
            Class<?> loadedClass = classLoader.loadClass(className);

            // 检查该类是否实现了 Job 接口
            if (!Job.class.isAssignableFrom(loadedClass)) {
                System.out.println("The class does not implement the required Job interface.");
                return;
            }

            // 创建实例并调用方法
            Job jobInstance = (Job) loadedClass.getDeclaredConstructor().newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
