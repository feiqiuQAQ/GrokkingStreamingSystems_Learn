package com.streamwork.ch02.job;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Source;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * ClassName: CountinuousVehicleSource
 * Package: com.streamwork.ch02.job
 * Description: 自动生成事件，使用interval参数控制生成速度
 *
 * @Author: Zhou Chengyu
 * @Create: 2025/1/15 - 19:18
 * @Version: v1.0
 */
public class CountinuousVehicleSource extends Source {
    public final List<String> vehiceList = Arrays.asList("van", "car", "truck");

    public int interval = 1000;

    // 传感器读取器的实现
    public CountinuousVehicleSource(String name, int interval) {
        // 把name赋给父类
        super(name);

        this.interval = interval;
    }

    // 复写父类抽象方法
    @Override
    public void getEvents(List<Event> eventCollector) {
        try {
            // 随机选择一个vehicle
            Random rand = new Random();
            String vehicle = vehiceList.get(rand.nextInt(vehiceList.size()));
            System.out.println("choose randomly: " + vehicle);

            Thread.sleep(interval);

            if (vehicle == null) {
                // null时退出
                // Exit when user closes the server.
                System.exit(0);
            }

            // 事件收集器增加一个事件
            eventCollector.add(new VehicleEvent(vehicle));
            System.out.println("");  // An empty line before logging new events
            System.out.println("CountinuousVehicleSource --> " + vehicle);

        }
        catch (InterruptedException e) {
            System.out.println("Interrupt abnormal: " + e);
        }

    }
}
