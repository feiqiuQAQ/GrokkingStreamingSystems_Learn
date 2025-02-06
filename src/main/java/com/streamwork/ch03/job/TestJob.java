package com.streamwork.ch03.job;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.func.ApplyFunc;
import com.streamwork.ch03.api.MapFunction;

import java.util.List;

public class TestJob {
    public ApplyFunc applyFunc = (Event event, List<Event> eventCollector) -> new VehicleEvent(event.getData() + "_map");
}
