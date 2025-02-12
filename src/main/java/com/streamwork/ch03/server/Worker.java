package com.streamwork.ch03.server;

import com.streamwork.ch03.api.DistributedOperator;
import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.engine.EventQueue;
import com.streamwork.ch03.engine.OperatorExecutor;
import com.streamwork.ch03.func.ApplyFunc;
import com.streamwork.ch03.job.VehicleEvent;
import com.streamwork.ch03.rpc.io.RpcNode;

import java.util.ArrayList;
import java.util.List;

public class Worker extends RpcNode {
    // 负责新开线程运行指定的任务（带并行度）
    // 线程间共享内存，将接收到的事件放入对应的事件队列
    // 可以读取配置
    public OperatorExecutor executor;

    // 设置队列容量
    private final static int QUEUE_SIZE = 64;

    // This list is used for accepting events from user logic.
    protected final List<Event> eventCollector = new ArrayList<Event>();
    // Data queues for the upstream processes
    protected EventQueue incomingQueue1 = null;

    protected EventQueue incomingQueue2 = null;
    // Data queue for the downstream processes
    protected EventQueue outgoingQueue = null;


    public Worker(String operatorType, ApplyFunc func) {
        // 需求一：实现一个算子worker，可接收指定队列的事件
        DistributedOperator op = new DistributedOperator(operatorType, 2, func);
        this.executor = new OperatorExecutor(op);

        // 创建组件间的事件队列
        this.incomingQueue1 = new EventQueue(QUEUE_SIZE);
        this.incomingQueue2 = new EventQueue(QUEUE_SIZE);
        executor.setIncomingQueues(new EventQueue[]{incomingQueue1, incomingQueue2});

        this.outgoingQueue = new EventQueue(QUEUE_SIZE);
        executor.setOutgoingQueue(outgoingQueue);
    }

    public synchronized void work() {
        executor.start();
    }

    public synchronized void addIncomingQueue1(String type) {
//        System.out.println("addIncomingQueue1");
        Event event = new VehicleEvent(type);
        incomingQueue1.add(event);
    }

    public synchronized void addIncomingQueue2(String type) {
//        System.out.println("addIncomingQueue1");
        Event event = new VehicleEvent(type);
        incomingQueue2.add(event);
    }

}
