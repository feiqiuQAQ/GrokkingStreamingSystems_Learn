package com.streamwork.ch02.api;

/**
 * The base class for all components, including Source and Operator.
 * 所有组件的基类，包括数据源和执行算子
 */
public class Component {
  private String name;
  // The stream object is used to connect the downstream operators.
  // 创建联系下流算子的流
  private Stream outgoingStream = new Stream();

  public Component(String name) {
    this.name = name;
  }

  /**
   * Get the name of this component.
   * @return The name of this component.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the outgoing event stream of this component. The stream is used to connect
   * the downstream components.
   * @return
   */
  public Stream getOutgoingStream() {
    return outgoingStream;
  }
}
