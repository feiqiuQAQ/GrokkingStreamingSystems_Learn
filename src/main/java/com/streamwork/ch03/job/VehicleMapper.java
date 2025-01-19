package com.streamwork.ch03.job;

import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.api.MapFunction;
import com.streamwork.ch03.api.MapOperator;
import com.streamwork.ch03.api.RandomGrouping;

import java.io.Serializable;
import java.util.List;

public class VehicleMapper extends MapOperator implements Serializable {
    private static final long serialVersionUID = 1L;

    // 静态嵌套类实现 MapFunction
    private static class VehicleMapFunction implements MapFunction, Serializable {
        private static final long serialVersionUID = 2L;

        @Override
        public Event map(Event input) {
            return new VehicleEvent(input.getData() + "_map");
        }
    }

    public VehicleMapper(String name, int parallelism) {
        super(name, parallelism, new VehicleMapFunction(),new RandomGrouping()); // 使用静态嵌套类
    }
}