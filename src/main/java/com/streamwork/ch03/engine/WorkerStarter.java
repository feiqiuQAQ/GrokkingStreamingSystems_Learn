package com.streamwork.ch03.engine;

import com.alibaba.fastjson.JSON;
import com.streamwork.ch03.api.*;
import com.streamwork.ch03.common.Task;
import com.streamwork.ch03.func.ApplyFunc;
import com.streamwork.ch03.rpc.io.RpcNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkerStarter extends RpcNode {
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
    public WorkerStarter(Job job) {
        this.job = job;
    }


    public void start() {

        Task t = requireTask();
        Task t2 = requireTask();

        /* 从执行dag找到自己的算子任务 */
        ComponentExecutor executor = findExecutor(t);
        ComponentExecutor executor2 = findExecutor(t2);
        System.out.println("done");

        /* 初始化算子的输入输出队列 */


        // Set up executors for all the components.
//        setupComponentExecutors();

        // Start web server
//        new WebServer(job.getName(), connectionList).start();

        // All components are created now. Build the connections to connect the components together.
//        setupConnections();

        // Start all the processes.
//        startProcesses();
    }

    private Task requireTask() {
        Task arg = JSON.parseObject(
                call("127.0.0.1", 9992, "requireTask", new Object[]{}).toString(),
                Task.class);
        return arg;
    }

    /**
     * Create all source and operator executors.
     */
//    private void setupComponentExecutors() {
//        // Start from sources in the job and traverse components to create executors
//        // 为每个源都创建执行器
//        for (Source source: job.getSources()) {
//            SourceExecutor executor = new SourceExecutor(source);
//            executorList.add(executor);
//            // For each source, traverse the operations connected to it.
//            // 为每个源递归的创建联系
//            traverseComponent(source, executor);
//        }
//    }

    /* 找到指派的算子 */
    private ComponentExecutor findExecutor(Task task) {
        for (Source source: job.getSources()) {
            if (source.getName().equals(task.getTaskType())){
                SourceExecutor executor = new SourceExecutor(source);
                return executor;
            }
            SourceExecutor executor = new SourceExecutor(source);
            executorList.add(executor);
            // For each source, traverse the operations connected to it.
            // 为每个源递归的创建联系
            return traverseComponent(source, executor, task);
        }
        return null;
    }

    /**
     * Set up connections (intermediate queues) betw
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

    private ComponentExecutor traverseComponent(Component component, ComponentExecutor executor, Task task) {
        Stream stream = component.getOutgoingStream();

        for (Operator operator: stream.getAppliedOperators()) {
            if (operator.getName().equals(task.getTaskType())) {
                OperatorExecutor operatorExecutor = new OperatorExecutor(operator);
                return operatorExecutor;
            }
            OperatorExecutor operatorExecutor = new OperatorExecutor(operator);
            executorList.add(operatorExecutor);

            // 为组件添加连接
//            connectionList.add(new Connection(executor, operatorExecutor));
            // Setup executors for the downstream operators.
            return traverseComponent(operator, operatorExecutor, task);
        }
        return null;
    }
}
