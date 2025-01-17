package com.streamwork.ch03.api;

import java.io.Serializable;
import java.util.Random;

/**
 * ClassName: RandomGrouping
 * Package: com.streamwork.ch03.api
 * Description: 随机shuffle
 *
 * @Author: Zhou Chengyu
 * @Create: 2025/1/17 - 08:48
 * @Version: v1.0
 */
public class RandomGrouping implements GroupingStrategy, Serializable {
    private static final long serialVersionUID = -1257295231424647887L;

    public RandomGrouping() { }

    @Override
    public int getInstance(Event event, int parallelism) {
        Random random = new Random();
        int randomInt = random.nextInt(parallelism);
        return randomInt;
    }
}
