package com.streamwork.ch03.job;

import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.api.Source;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * ClassName: CountinuousVehicleSource
 * Package: com.streamwork.ch03.job
 * Description:
 *
 * @Author: Zhou Chengyu
 * @Create: 2025/1/17 - 08:59
 * @Version: v1.0
 */
public class ContinuousVehicleSource extends Source {
    private static final long serialVersionUID = 7153550920021993542L;

    private int instance = 0;

    public final List<String> vehiceList = Arrays.asList("van", "car", "truck");

    public int interval = 1000;

    // 传感器读取器的实现
    public ContinuousVehicleSource(String name, int parallelism, int interval) {
        // 把name赋给父类
        super(name, parallelism);

        this.interval = interval;
    }

    @Override
    public void setupInstance(int instance) {
        this.instance = instance;
    }

    // 复写父类抽象方法
    @Override
    public void getEvents(List<Event> eventCollector) {
        try {
            // 随机选择一个vehicle
            Random rand = new Random();
            String vehicle = vehiceList.get(rand.nextInt(vehiceList.size()));
            System.out.printf("Instance %d choose randomly: %s\n", instance,vehicle);

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