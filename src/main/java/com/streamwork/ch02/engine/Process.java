package com.streamwork.ch02.engine;

/**
 * This is the base class of all processes (executors). When a process is started,
 * a new thread is created to call the runOnce() function of the derived class.
 * Each process also have an incoming event queue and an outgoing event queue.
 * 这个所有执行器的基类，当一个process被创建了，一个新的线程会被创建并不停的调用下游的runOnce函数。
 */
public abstract class Process {
  private final Thread thread;

  public Process() {
    this.thread = new Thread() {
      public void run() {
        System.out.println("runOnce");
        while (runOnce());
      }
    };
  }

  /**
   * Start the process.
   */
  public void start() {
    thread.start();
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exit.
   */
  abstract boolean runOnce();
}
