package com.streamwork.ch03.job;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;

public class JobLoader {
    private final URLClassLoader classLoader;

    public JobLoader(String jarPath) throws Exception {
        File file = new File(jarPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Jar file not found: " + jarPath);
        }
        this.classLoader = new URLClassLoader(
                new URL[]{file.toURI().toURL()},
                getClass().getClassLoader()
        );
    }

    public void runMain(String className, String[] args) throws Exception {
        Class<?> clazz = classLoader.loadClass(className);
        Method mainMethod = clazz.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, (Object) args);
    }

    public void close() {
        try {
            classLoader.close();
        } catch (Exception e) {
            // TODO
        }
    }
}
