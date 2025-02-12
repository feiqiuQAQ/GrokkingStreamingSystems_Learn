package com.streamwork.ch03.api;

import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.api.Operator;
import com.streamwork.ch03.func.ApplyFunc;

import java.io.Serializable;
import java.util.List;

public class DistributedOperator extends Operator implements Serializable {
    public ApplyFunc applyFunc;

    protected int instance = 0;

    @Override
    public void setupInstance(int instance) {
        this.instance = instance;
    }

    public DistributedOperator(String name, int parallelism, ApplyFunc func) {
        super(name, parallelism);
        this.applyFunc = func;
    }

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        System.out.println("Distributed operator " + getName() + " called");
        applyFunc.apply(event, eventCollector);
        eventCollector.add(event);
    }
}
