package com.streamwork.ch02.api;

import java.util.List;

/**
 * This Operator class is the base class for all user defined operators.
 * 操作算子类是所有用户定义算子的基类
 */
public abstract class Operator extends Component {
  public Operator(String name) {
    super(name);
  }

  /**
   * Apply logic to the incoming event and generate results.
   * The function is abstract and needs to be implemented by users.
   * 应用逻辑到获取的时间并生成结果，这个函数是一个抽象函数需要用户自己实现
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public abstract void apply(Event event, List<Event> eventCollector);
}
