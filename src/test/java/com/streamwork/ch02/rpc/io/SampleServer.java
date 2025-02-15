/*
 * SampleServer.java
 * Copyright 2021 Razertory, all rights reserved.
 * GUSU PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.streamwork.ch02.rpc.io;


import com.alibaba.fastjson.JSON;
import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.func.ApplyFunc;
import com.streamwork.ch03.job.VehicleEvent;
import com.streamwork.ch03.common.Serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.streamwork.ch03.job.Task;
/**
 * @author gusu
 * @date 2021/6/26
 */
public class SampleServer extends RpcNode {

    // foo方法接收封装的 Task 类实例
    public void foo(String taskBytes) throws IOException, ClassNotFoundException {
        // 反序列化传递的 Task 类实例
        Task task = JSON.parseObject(taskBytes, Task.class);
        System.out.println("Executing task on the server side");

        // 创建事件并调用 Task 的 execute 方法
        Event event = new VehicleEvent("car");
        List<Event> events = new ArrayList<>();
        task.execute(event, events);  // 执行封装类中的逻辑

        // 输出事件信息
        System.out.println("Event collected: " + events.size() + " events");
    }
}
