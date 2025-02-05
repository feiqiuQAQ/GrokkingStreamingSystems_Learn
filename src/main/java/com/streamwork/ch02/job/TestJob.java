package com.streamwork.ch02.job;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.func.ApplyFunc;

import java.util.List;

public class TestJob {
    public ApplyFunc applyFunc = (Event event, List<Event> eventCollector) -> System.out.println("Runnable is running:");
}
