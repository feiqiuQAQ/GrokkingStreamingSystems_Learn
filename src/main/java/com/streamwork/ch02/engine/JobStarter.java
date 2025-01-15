package com.streamwork.ch02.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.streamwork.ch02.api.Component;
import com.streamwork.ch02.api.Job;
import com.streamwork.ch02.api.Operator;
import com.streamwork.ch02.api.Source;
import com.streamwork.ch02.api.Stream;


// Job启动的入口类
public class JobStarter {

  // 设置队列的容量
  private final static int QUEUE_SIZE = 64;

  // The job to start
  private final Job job;

  // 操作算子的列表
  // List of executors
  private final List<ComponentExecutor> executorList = new ArrayList<ComponentExecutor>();

  // Connections between component executors
  // 执行器间的Connections
  private final List<Connection> connectionList = new ArrayList<Connection>();

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
   * 创建所有的源算子和操作算子
   */
  private void setupComponentExecutors() {
    // Start from sources in the job and traverse components to create executors
    for (Source source: job.getSources()) {
      // 对于每个源，都获取它们的执行器
      SourceExecutor executor = new SourceExecutor(source);
      executorList.add(executor);
      // For each source, traverse the operations connected to it.
      // 对于每个源，遍历操作算子以连接它们
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
  }

  private void connectExecutors(Connection connection) {
    // It is a newly connected operator executor. Note that in this version, there is no
    // shared "from" component and "to" component. The job looks like a single linked list.
    // 创建组件间的事件队列
    EventQueue intermediateQueue = new EventQueue(QUEUE_SIZE);
    connection.from.setOutgoingQueue(intermediateQueue);
    connection.to.setIncomingQueue(intermediateQueue);
  }

  private void traverseComponent(Component component, ComponentExecutor executor) {
    // 获取数据源的stream
    Stream stream = component.getOutgoingStream();

    // 递归连接操作算子和源组件
    for (Operator operator: stream.getAppliedOperators()) {
      OperatorExecutor operatorExecutor = new OperatorExecutor(operator);
      executorList.add(operatorExecutor);
      connectionList.add(new Connection(executor, operatorExecutor));
      // Setup executors for the downstream operators.
      traverseComponent(operator, operatorExecutor);
    }
  }
}
