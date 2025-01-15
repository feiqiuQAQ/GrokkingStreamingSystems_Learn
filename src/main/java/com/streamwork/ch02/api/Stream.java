package com.streamwork.ch02.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Stream class represents a data stream coming out of a component.
 * Operators with the correct type can be applied to this stream.
 * 一个流代表了一段从一个组件中输出的数据流
 * Example:
 *   Job job = new Job("my_job");
 *   job.addSource(mySource)
 *      .applyOperator(myOperator);
 */
public class Stream {
  // List of all operators to be applied to this stream.
  // 存储所有被应用在这个流上的操作算子集合
  private final Set<Operator> operatorSet = new HashSet<Operator>();

  /**
   * Apply an operator to this stream.
   * 增加一个操作算子到这个流上，返回一个这个算子的输出流
   * @param operator The operator to be connected to the current stream
   * @return The outgoing stream of the operator.
   */
  public Stream applyOperator(Operator operator) {
    if (operatorSet.contains(operator)) {
      throw new RuntimeException("Operator " + operator.getName() + " is added to job twice");
    }

    operatorSet.add(operator);
    return operator.getOutgoingStream();
  }

  /**
   * Get the collection of operators applied to this stream.
   * 获得所有应用到这个流的上的操作算子集合
   * @return The collection of operators applied to this stream
   */
  public Collection<Operator> getAppliedOperators() {
    return operatorSet;
  }
}
