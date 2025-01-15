package com.streamwork.ch02.job;

import com.streamwork.ch02.api.Job;
import com.streamwork.ch02.api.Stream;
import com.streamwork.ch02.engine.JobStarter;

// 组装job的逻辑
public class VehicleCountJob {

  public static void main(String[] args) {
    Job job = new Job("vehicle_count");

    // 为job增加一个数据源CountinuousVehicleSource,每隔1秒随机生产数据
    Stream bridgeStream = job.addSource(new CountinuousVehicleSource("CountinuousVehicleSource-reader", 1000));
    // 为流增加一个计数操作算子
    bridgeStream.applyOperator(new VehicleCounter("vehicle-counter"));

    System.out.println("This is a streaming job that counts vehicles in real time. " +
        "Please enter vehicle types like 'car' and 'truck' in the input terminal " +
        "and look at the output");

    // 开启job
    JobStarter starter = new JobStarter(job);
    starter.start();
  }
}
