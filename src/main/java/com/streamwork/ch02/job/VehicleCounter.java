package com.streamwork.ch02.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Operator;

// 实现计数算子
class VehicleCounter extends Operator {

  // 创建存储数据的map数据结构
  private final Map<String, Integer> countMap = new HashMap<String, Integer>();

  public VehicleCounter(String name) {  super(name);  }


  // 重写应用逻辑方法
  @Override
  public void apply(Event vehicleEvent, List<Event> eventCollector) {
    // 从事件中获取数据
    String vehicle = ((VehicleEvent)vehicleEvent).getData();
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    System.out.println("VehicleCounter --> ");
    printCountMap();
  }

  // 打印逻辑
  private void printCountMap() {
    List<String> vehicles = new ArrayList<>(countMap.keySet());
    Collections.sort(vehicles);

    for (String vehicle: vehicles) {
      System.out.println("  " + vehicle + ": " + countMap.get(vehicle));
    }
  }
}
