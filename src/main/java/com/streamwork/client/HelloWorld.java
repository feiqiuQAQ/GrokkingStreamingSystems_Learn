package com.streamwork.client;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        for (String arg : args) {
            System.out.println("Argument: " + arg);
        }
    }
}