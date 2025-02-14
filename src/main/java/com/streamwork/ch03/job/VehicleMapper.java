package com.streamwork.ch03.job;

import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.api.MapFunction;
import com.streamwork.ch03.api.MapOperator;
import com.streamwork.ch03.api.RandomGrouping;

import java.io.Serializable;
import java.util.List;

public class VehicleMapper extends MapOperator {
    private static final long serialVersionUID = 1L;

    public VehicleMapper(String name, int parallelism) {
        super(name, parallelism, (MapFunction) input -> new VehicleEvent(input.getData() + "_map"));
    }
}