package com.streamwork.ch03.api;

import java.io.Serializable;
import java.util.List;

public class MapOperator extends Operator implements Serializable {
    private static final long serialVersionUID = -1972993710318354151L;

    protected int instance = 0;

    private final MapFunction mapFunction;

    public MapOperator(String name, int parallelism, MapFunction mapFunction) {
        super(name, parallelism);
        this.mapFunction = mapFunction;
    }

    public MapOperator(String name, int parallelism, MapFunction mapFunction, GroupingStrategy grouping) {
        super(name, parallelism, grouping);
        this.mapFunction = mapFunction;
    }

    @Override
    public void setupInstance(int instance) {
        this.instance = instance;
    }

    @Override
    public void apply(Event inputEvent, List<Event> eventCollector) {
        // 调用用户提供的 mapFunction 处理输入事件
        Event outputEvent = mapFunction.map(inputEvent);
        eventCollector.add(outputEvent);

        System.out.println("mapOperator :: instance " + instance + " --> " + outputEvent.getData());
    }

}
