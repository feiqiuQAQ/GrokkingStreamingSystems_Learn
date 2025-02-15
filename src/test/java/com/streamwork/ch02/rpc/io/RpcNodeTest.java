package com.streamwork.ch02.rpc.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.streamwork.ch03.common.Serializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.job.Task;
import com.streamwork.ch03.func.ApplyFunc;
import com.streamwork.ch03.job.VehicleEvent;
import com.streamwork.ch03.job.VehicleEventMapper;
import org.junit.Assert;
import org.junit.Test;

public class RpcNodeTest {

    @Test
    public void testCallMethod() throws Exception {
        // 创建服务端实例
        SampleServer sampleServer1 = new SampleServer();
        SampleServer sampleServer2 = new SampleServer();

        // 创建 Task 类实例，并设置属性
        Task task = new Task("Data Processing Task", 5);

        // 将 Task 类序列化为 JSON 字符串
        String taskBytes = JSON.toJSONString(task, SerializerFeature.WriteClassName);

        // 启动服务端线程
        Runnable run = () -> {
            try {
                sampleServer2.serve();  // 启动服务端，监听请求
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(run);
        thread.start();

        // RPC 调用，传递 Task 类实例的 JSON 字符串
        Object r = sampleServer1.call(sampleServer2.getPort(), "foo", new Object[]{taskBytes});

        // 可选：解析并验证返回结果
        // JSON.parseObject(r.toString(), Task.class);  // 如果返回的是 Task 实例
    }
}