package com.streamwork.ch03.api;

public interface GroupingStrategy {
  /**
   * Get target instance id from an event and component parallelism.
   * Note that in this implementation, only one instance is selected.
   * This can be easily extended if needed.
   * 获取实例id的策略，这个借口可以很容易被扩展
   * @param event The event object to route to the component.
   * @param The parallelism of the component.
   * @return The integer key of this event.
   */
  int getInstance(Event event, int parallelism);
}
