package com.streamwork.ch03.engine;

import com.streamwork.ch03.api.Component;

/**
 * The base class for executors of source and operator.
 */
public abstract class ComponentExecutor {
  protected Component component;
  protected InstanceExecutor [] instanceExecutors; // 执行器实例列表

  public ComponentExecutor(Component component) {
    this.component = component;
    int parallelism = component.getParallelism();
    this.instanceExecutors = new InstanceExecutor[parallelism];
  }

  /**
   * Start instance executors (real processes) of this component.
   */
  public abstract void start();

  /**
   * Get the instance executors of this component executor.
   */
  public InstanceExecutor [] getInstanceExecutors() {
    return instanceExecutors;
  }

  public Component getComponent() {
    return component;
  }


  // 为每一个执行器实例都绑定输入流和输出流
  public void setIncomingQueues(EventQueue [] queues) {
    for (int i = 0; i < queues.length; ++i) {
      instanceExecutors[i].setIncomingQueue(queues[i]);
    }
  }

  public void setOutgoingQueue(CommonEventQueue queue) {
    for (InstanceExecutor instance: instanceExecutors) {
      instance.setOutgoingQueue(queue);
    }
  }
}
