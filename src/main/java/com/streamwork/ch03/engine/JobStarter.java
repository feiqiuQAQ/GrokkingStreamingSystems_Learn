package com.streamwork.ch03.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.streamwork.ch03.api.Component;
import com.streamwork.ch03.api.Job;
import com.streamwork.ch03.api.Operator;
import com.streamwork.ch03.api.Source;
import com.streamwork.ch03.api.Stream;

public class JobStarter {
  // 设置队列容量
  private final static int QUEUE_SIZE = 64;

  // The job to start
  private final Job job;
  // List of executors and stream managers执行器队列和流管理器队列
  private final List<ComponentExecutor> executorList = new ArrayList<ComponentExecutor>();
  private final List<EventDispatcher> dispatcherList = new ArrayList<EventDispatcher>();

  // Connections between component executors 组件间的连接器
  private final List<Connection> connectionList = new ArrayList<Connection>();

  // 把job赋给该实例类
  public JobStarter(Job job) {
    this.job = job;
  }


  public void start() {
    // Set up executors for all the components.
    setupComponentExecutors();

    // All components are created now. Build the connections to connect the components together.
    setupConnections();

    // Start all the processes.
    startProcesses();

    // Start web server
    new WebServer(job.getName(), connectionList).start();
  }

  /**
   * Create all source and operator executors.
   */
  private void setupComponentExecutors() {
    // Start from sources in the job and traverse components to create executors
    // 为每个源都创建执行器
    for (Source source: job.getSources()) {
      SourceExecutor executor = new SourceExecutor(source);
      executorList.add(executor);
      // For each source, traverse the operations connected to it.
      // 为每个源递归的创建联系
      traverseComponent(source, executor);
    }
  }

  /**
   * Set up connections (intermediate queues) between all component executors.
   */
  private void setupConnections() {
    for (Connection connection: connectionList) {
      connectExecutors(connection);
    }
  }

  /**
   * Start all the processes for the job.
   */
  private void startProcesses() {
    Collections.reverse(executorList);
    for (ComponentExecutor executor: executorList) {
      executor.start();
    }
    for (EventDispatcher dispatcher: dispatcherList) {
      dispatcher.start();
    }
  }

  private void connectExecutors(Connection connection) {
    // Each component executor could connect to multiple downstream operator executors.
    // 每个执行器组件可以连接多个下游执行器算子
    // For each of the downstream operator executor, there is a stream manager.
    // 对每个下游操作执行器，都有一个流管理器
    // Each instance executor of the upstream executor connects to the all the stream managers
    // of the downstream executors first. And each stream manager connects to all the instance
    // executors of the downstream executor.
    // 上游执行器的每个执行器实例首先连接下游的全部流管理器，每个流管理器连接所有的下游执行器实例
    // Note that in this version, there is no shared "from" component and "to" component.
    // The job looks like a single linked list.
    EventDispatcher dispatcher = new EventDispatcher(connection.to);
    dispatcherList.add(dispatcher);

    // Connect to upstream.
    EventQueue upstream = new EventQueue(QUEUE_SIZE);
    connection.from.setOutgoingQueue(upstream);
    dispatcher.setIncomingQueue(upstream);

    // Connect to downstream (to each instance).
    int parallelism = connection.to.getComponent().getParallelism();
    EventQueue [] downstream = new EventQueue[parallelism];
    for (int i = 0; i < parallelism; ++i) {
      downstream[i] = new EventQueue(QUEUE_SIZE);
    }
    connection.to.setIncomingQueues(downstream);
    dispatcher.setOutgoingQueues(downstream);
  }

  private void traverseComponent(Component component, ComponentExecutor executor) {
    Stream stream = component.getOutgoingStream();

    for (Operator operator: stream.getAppliedOperators()) {
      OperatorExecutor operatorExecutor = new OperatorExecutor(operator);
      executorList.add(operatorExecutor);

      // 为组件添加连接
      connectionList.add(new Connection(executor, operatorExecutor));
      // Setup executors for the downstream operators.
      traverseComponent(operator, operatorExecutor);
    }
  }
}
