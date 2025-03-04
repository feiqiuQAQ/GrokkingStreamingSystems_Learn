package io;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class RpcNodeTest {

    @Test
    public void testCallMethod() throws Exception {
        SampleServer sampleServer1 = new SampleServer();
        SampleServer sampleServer2 = new SampleServer();
        Runnable run = () -> {
            try {
                sampleServer2.serve();
            } catch (Exception e) {
            }
        };
        Thread thread = new Thread(run);
        thread.start();
        Object r = sampleServer1
            .call("192.0.0.2" ,sampleServer2.getPort(), "foo", new Object[]{"1"});

        Assert.assertEquals(sampleServer2.foo("1"), JSON.parseObject(r.toString(), Map.class));
    }


}