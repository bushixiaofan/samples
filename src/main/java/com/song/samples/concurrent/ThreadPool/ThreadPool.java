package com.song.samples.concurrent.ThreadPool;

import java.util.concurrent.Callable;

/**
 * 一个简单线程池接口定义
 *
 * @param <Job>
 */
public interface ThreadPool<Job extends Runnable > {

    /**
     * 执行一个job，这个job需要实现Runnable接口
     */
    void execute(Job job);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 添加工作者线程
     */
    void addWorkers(int num);

    /**
     * 减少工作者线程
     */
    void removeWorkers(int num);

    /**
     * 得到正在执行的线程数量
     * @return
     */
    int getJobSize();
}
