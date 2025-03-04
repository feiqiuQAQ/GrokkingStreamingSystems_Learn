package com.streamwork.ch03.api;

import java.io.Serializable;
import java.util.List;

/**
 * This Operator class is the base class for all user defined operators.
 */
public abstract class Operator extends Component implements Serializable {
  private static final long serialVersionUID = -1972993710318354151L;

  // Grouping strategy for the incoming data
  private final GroupingStrategy grouping;

  public Operator(String name, int parallelism) {
    super(name, parallelism);
    this.grouping = new ShuffleGrouping();  // Default 默认为shuffleGrouping
  }

  public Operator(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism);
    this.grouping = grouping;
  }

  /**
   * Set up instance.
   * 设置实例id
   * @param instance The instance id (an index starting from 0) of this source instance.
   */
  public abstract void setupInstance(int instance);

  /**
   * Apply logic to the incoming event and generate results.
   * The function is abstract and needs to be implemented by users.
   * 应用逻辑到输入事件并生成结果
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public abstract void apply(Event event, List<Event> eventCollector);

  /**
   * Get the grouping key of an event.
   * 返回分组策略
   * @return The grouping strategy of this operator
   */
  public GroupingStrategy getGroupingStrategy() {
    return grouping;
  }
}
