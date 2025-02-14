package com.streamwork.ch03.func;

import com.streamwork.ch03.api.Event;

import java.io.Serializable;
import java.util.List;

public interface ApplyFunc extends Serializable {

    /**
     * 应用逻辑到获取的时间并生成结果，这个函数是一个抽象函数需要用户自己实现
     * @param event The incoming event
     * @param eventCollector The outgoing event collector
     */
    public void apply(Event event, List<Event> eventCollector);
}
