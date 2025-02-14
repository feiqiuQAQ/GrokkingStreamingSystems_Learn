/*
 * SampleServer.java
 * Copyright 2021 Razertory, all rights reserved.
 * GUSU PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.streamwork.ch02.rpc.io;


import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.func.ApplyFunc;
import com.streamwork.ch03.job.VehicleEvent;
import com.streamwork.ch03.job.VehicleEventMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gusu
 * @date 2021/6/26
 */
public class SampleServer extends RpcNode {

    public void foo(VehicleEventMapper func) {
        Event event = new VehicleEvent("car");
        List<Event> events = new ArrayList<Event>();
        func.apply(event, events);
    }

    public void apply(Event event, List<Event> eventCollector) {

    }
}
