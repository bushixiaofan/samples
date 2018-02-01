package com.song.samples.concurrent.concurrentutils;

import java.util.Map;
import java.util.concurrent.*;

/**
 * CyclicBarrier使用示例
 */
public class BankWaterService implements Runnable {

    // 任务数量
    private int taskNum;
    // 线程数量
    private int threadNum;
    // 创建taskNum个屏障，处理完成后执行当前类的run方法
    private CyclicBarrier c;

    // 启动taskNum个线程（开发中线程池应该是单例的，多个service注入一个线程池）
    private ExecutorService executor;

    // 保存每个任务的计算结果
    private ConcurrentHashMap<String, Integer> count = new ConcurrentHashMap<>();

    public BankWaterService(int taskNum, int threadNum) {
        this.taskNum = taskNum;
        this.threadNum = threadNum;
        this.c = new CyclicBarrier(taskNum, this);
        this.executor = Executors.newFixedThreadPool(threadNum);
    }

    private void count() {
        for(int i = 0; i < taskNum; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    count.put(Thread.currentThread().getName(), 1);
                    // 计算完成，插入一个屏障
                    try {
                        c.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    @Override
    public void run() {
        int result = 0;
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            result += entry.getValue();
        }
        // 显式关闭线程池
        executor.shutdownNow();
        System.out.println(result);
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService(4, 4);
        bankWaterService.count();
    }
}
