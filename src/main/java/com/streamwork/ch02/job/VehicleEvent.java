package com.streamwork.ch02.job;

import com.streamwork.ch02.api.Event;


// 交通工具事件类
public class VehicleEvent extends Event {
  private final String type;

  public VehicleEvent(String type) {
    this.type = type;
  }

  @Override
  public String getData() {
    return type;
  }
}
