package com.streamwork.ch02.job;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Operator;

import java.util.List;

/**
 * ClassName: CalculateSumTotalExcutor
 * Package: com.streamwork.ch02.job
 * Description:
 *
 * @Author: Zhou Chengyu
 * @Create: 2025/1/15 - 20:27
 * @Version: v1.0
 */
public class CalculateSumTotalExcutor extends Operator {
    private int total = 0;

    public CalculateSumTotalExcutor(String name) {  super(name);  }

    // 重写应用逻辑方法
    @Override
    public void apply(Event vehicleEvent, List<Event> eventCollector) {
        total += 1;
        System.out.println("Total sum: " + total);
    }

}
