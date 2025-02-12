package com.streamwork.ch03.job;

import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.func.ApplyFunc;
import com.streamwork.ch03.job.VehicleEvent;

import java.io.Serializable;
import java.util.List;

public class VehicleEventMapper implements ApplyFunc, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        VehicleEvent e = new VehicleEvent(event.getData() + "_map");
        System.out.println(e.toString());
    }
}
