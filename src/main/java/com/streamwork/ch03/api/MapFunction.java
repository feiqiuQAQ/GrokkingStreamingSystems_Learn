package com.streamwork.ch03.api;

public interface MapFunction {
    /**
     *
     * @return The result of event after map.
     */
    Event map(Event input);
}
