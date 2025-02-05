package com.streamwork.ch02.server;

import com.alibaba.fastjson.JSON;
import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.job.TestJob;
import com.streamwork.ch02.job.VehicleEvent;
import com.streamwork.ch02.rpc.io.RpcNode;

// 负责解析客户端提交的Job，编排Dag并生成工作任务，启动Master节点和Worker节点
public class Distributed extends RpcNode {
    public static void main(String[] args) throws Exception {
        TestJob testJob = new TestJob();
        final Worker worker = new Worker("map", testJob.applyFunc);
//        worker.work();
        worker.setPort(9992);
        worker.serve();

        worker.incomingQueue.add(new VehicleEvent("test"));

        worker.incomingQueue.add(new VehicleEvent("test1"));

//        VehicleEvent event2 = new VehicleEvent("test2");

        Object result = worker.call(9992, "addIncomingQueue", new Object[]{"test2"});

        Object result2 = worker.call(9992, "work", new Object[]{});

        Object result3 = worker.call(9992, "addIncomingQueue", new Object[]{"test3"});
    }
}


