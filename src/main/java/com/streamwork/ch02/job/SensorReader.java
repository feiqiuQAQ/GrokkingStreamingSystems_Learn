package com.streamwork.ch02.job;

import java.net.*;
import java.io.*;
import java.util.List;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Source;

class SensorReader extends Source {
  // 读取缓冲区数据的结构
  private final BufferedReader reader;

  // 传感器读取器的实现
  public SensorReader(String name, int port) {
    // 把name赋给父类
    super(name);

    //设置一个套接字监听器
    reader = setupSocketReader(port);
  }

  // 复写父类抽象方法
  @Override
  public void getEvents(List<Event> eventCollector) {
    try {
      // 读取套接字上的一行
      String vehicle = reader.readLine();
      if (vehicle == null) {
        // null时退出
        // Exit when user closes the server.
        System.exit(0);
      }
      // 事件收集器增加一个事件
      eventCollector.add(new VehicleEvent(vehicle));
      System.out.println("");  // An empty line before logging new events
      System.out.println("SensorReader --> " + vehicle);
    } catch (IOException e) {
      System.out.println("Failed to read input: " + e);
    }
  }

  private BufferedReader setupSocketReader(int port) {
    try {
      // 获取套接字
      Socket socket = new Socket("localhost", port);
      // 获取输入流
      InputStream input = socket.getInputStream();
      // 返回一个带缓冲区的InputStreamReader
      return new BufferedReader(new InputStreamReader(input));
    } catch (UnknownHostException e) {
      e.printStackTrace();
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
    return null;
  }
}
