package com.streamwork.ch03.common;

import com.streamwork.ch03.api.Component;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Task {
    private Integer id;
    private String taskType; // source or operator
    private Integer parallelism;
    private String ipaddress;
    private Component component;
}
