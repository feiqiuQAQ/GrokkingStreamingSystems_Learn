package com.streamwork.ch03.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Task {
    private Integer id;
    private String taskType;
    private Integer parallelism;
    private String ipaddress;
}
