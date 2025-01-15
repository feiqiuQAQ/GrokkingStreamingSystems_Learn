package com.streamwork.ch02.api;

/**
 * This is the base class for all the event classes.
 * Users should extend this class to implement all their own event classes.
 * 所有事件类的基类，用户可以拓展这个类去实现自己的事件类
 */
public abstract class Event {
  /**
   * Get data stored in the event.
   * 获取存储在事件中的数据
   * @return The data stored in the event
   */
  public abstract Object getData();
}
