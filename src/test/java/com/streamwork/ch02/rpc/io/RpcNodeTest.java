package com.streamwork.ch02.rpc.io;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.func.ApplyFunc;
import com.streamwork.ch03.job.VehicleEvent;
import com.streamwork.ch03.job.VehicleEventMapper;
import org.junit.Assert;
import org.junit.Test;

public class RpcNodeTest {

    @Test
    public void testCallMethod() throws Exception {
        SampleServer sampleServer1 = new SampleServer();
        SampleServer sampleServer2 = new SampleServer();
        VehicleEventMapper func = new VehicleEventMapper();

        Runnable run = () -> {
            try {
                sampleServer2.serve();
            } catch (Exception e) {
            }
        };
        Thread thread = new Thread(run);
        thread.start();
        Object r = sampleServer1
            .call(sampleServer2.getPort(), "foo", new Object[]{func});
        JSON.parseObject(r.toString(), ApplyFunc.class);
//        Assert.assertEquals(sampleServer2.foo("1"), JSON.parseObject(r.toString(), Map.class));
    }


}