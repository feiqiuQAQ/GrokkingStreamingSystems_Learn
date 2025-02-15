package com.streamwork.ch03.job;

import com.streamwork.ch03.api.Event;
import java.util.List;

public class Task {

    // 类的属性
    private String taskName;
    private int priority;

    public Task(String taskName, int priority) {
        this.taskName = taskName;
        this.priority = priority;
    }

    // Getter 和 Setter 方法
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    // 执行任务的方法
    public void execute(Event event, List<Event> eventCollector) {
        System.out.println("Executing task: " + taskName + " with priority: " + priority);
        // 执行任务的业务逻辑

        eventCollector.add(event);
    }
}
