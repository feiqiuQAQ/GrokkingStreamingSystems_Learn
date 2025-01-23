package com.streamwork.ch02.server;

import com.streamwork.ch02.api.Component;
import com.streamwork.ch02.api.DistributedOperator;
import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Operator;
import com.streamwork.ch02.engine.EventQueue;
import com.streamwork.ch02.engine.OperatorExecutor;
import com.streamwork.ch02.func.ApplyFunc;
import com.streamwork.ch02.job.VehicleEvent;
import com.streamwork.ch02.rpc.io.RpcNode;

import java.util.ArrayList;
import java.util.List;

public class Worker extends RpcNode {
    // 负责新开线程运行指定的任务（带并行度）
    // 线程间共享内存，将接收到的事件放入对应的事件队列
    // 可以读取配置
    public OperatorExecutor executor;

    // This list is used for accepting events from user logic.
    protected final List<Event> eventCollector = new ArrayList<Event>();
    // Data queues for the upstream processes
    protected EventQueue incomingQueue = null;
    // Data queue for the downstream processes
    protected EventQueue outgoingQueue = null;


    public Worker(String operatorType, ApplyFunc func) {
        // 需求一：实现一个算子worker，可接收指定队列的事件
        DistributedOperator op = new DistributedOperator(operatorType, func);
        this.executor = new OperatorExecutor(op);

        // 创建组件间的事件队列
        this.incomingQueue = new EventQueue(60);
        executor.setIncomingQueue(incomingQueue);

        this.outgoingQueue = new EventQueue(60);
        executor.setOutgoingQueue(outgoingQueue);
    }

    public void work() {
        executor.start();
    }

    public void addIncomingQueue() {
        System.out.println("addIncomingQueue");
//        incomingQueue.add(event);
    }
}
