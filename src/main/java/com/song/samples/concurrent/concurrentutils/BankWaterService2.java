package com.song.samples.concurrent.concurrentutils;

import java.util.Map;
import java.util.concurrent.*;

/**
 * CountDownLatch测试
 */
public class BankWaterService2 {
    // 任务数量
    private int taskNum;
    // 线程数量
    private int threadNum;
    // 创建taskNum个屏障，处理完成后执行当前类的run方法
    private CountDownLatch c;

    // 启动taskNum个线程（开发中线程池应该是单例的，多个service注入一个线程池）
    private ExecutorService executor;

    // 保存每个任务的计算结果
    private ConcurrentHashMap<String, Integer> count = new ConcurrentHashMap<>();

    public BankWaterService2(int taskNum, int threadNum) {
        this.taskNum = taskNum;
        this.threadNum = threadNum;
        this.c = new CountDownLatch(taskNum);
        this.executor = Executors.newFixedThreadPool(threadNum);
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public CountDownLatch getC() {
        return c;
    }

    public void setC(CountDownLatch c) {
        this.c = c;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public ConcurrentHashMap<String, Integer> getCount() {
        return count;
    }

    public void setCount(ConcurrentHashMap<String, Integer> count) {
        this.count = count;
    }

    private void count() {
        for(int i = 0; i < taskNum; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    count.put(Thread.currentThread().getName(), 1);
                    c.countDown();
                }
            });
        }
    }

    public void run() {
        int result = 0;
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            result += entry.getValue();
        }
        // 显式关闭线程池
        executor.shutdownNow();
        System.out.println(result);
    }

    public static void main(String[] args) throws InterruptedException {
        BankWaterService2 bankWaterService = new BankWaterService2(4, 4);

        bankWaterService.count();

        CountDownLatch c = bankWaterService.getC();
        c.await();
        bankWaterService.run();
    }
}
