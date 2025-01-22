package com.streamwork.ch03.api;

import java.io.Serializable;

public interface MapFunction extends Serializable {
    /**
     *
     * @return The result of event after map.
     */
    Event map(Event input);
}
