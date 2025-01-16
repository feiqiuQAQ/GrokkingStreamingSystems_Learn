package com.streamwork.ch02.job;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Operator;

import java.util.*;

/**
 * ClassName: VehicleChargeCouter
 * Package: com.streamwork.ch02.job
 * Description:
 *
 * @Author: Zhou Chengyu
 * @Create: 2025/1/15 - 19:50
 * @Version: v1.0
 */
public class VehicleChargeCouter extends Operator {
    // 创建存储数据的map数据结构
    private final Map<String, Integer> countMap = new HashMap<String, Integer>();

    public static final Map<String, Double> CAR_PRICES = Map.of(
            "van", 7.99,
            "car", 4.50,
            "truck", 39.75
    );

    private double charge_amount;

    public VehicleChargeCouter(String name) {  super(name);  }


    // 重写应用逻辑方法
    @Override
    public void apply(Event vehicleEvent, List<Event> eventCollector) {
        // 从事件中获取数据
        String vehicle = ((VehicleEvent)vehicleEvent).getData();
        Integer count = countMap.getOrDefault(vehicle, 0) + 1;
        countMap.put(vehicle, count);
        charge_amount += CAR_PRICES.getOrDefault(vehicle, 9.99);

        System.out.println("VehicleCounter --> ");
        printCountMap();

        // 将事件传递给下游算子
        eventCollector.add(vehicleEvent);
    }

    // 打印逻辑
    private void printCountMap() {
        List<String> vehicles = new ArrayList<>(countMap.keySet());
        Collections.sort(vehicles);

        for (String vehicle: vehicles) {
            System.out.println("  " + vehicle + ": " + countMap.get(vehicle));
        }

        System.out.printf("Charge Amount : %.2f%n", charge_amount);
    }
}
