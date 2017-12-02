package com.song.samples.Utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Scope("singleton")
@Component
public class ExecutorUtil<V> implements InitializingBean, DisposableBean {

    private ThreadPoolExecutor executor;
    private int coreSize = 5;
    private int maxSize = 50;

    public void reset(int coreSize, int maxSize) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        executor.setCorePoolSize(coreSize);
        executor.setMaximumPoolSize(maxSize);
        executor.setKeepAliveTime(60, TimeUnit.SECONDS);
    }

    public Future<V> submit(Callable<V> task) {
        Future future = executor.submit(task);
        int length = executor.getQueue().size();
//        UnifyLogger.info(LogIdConst.INFO, false, "线程池队列长度！" + length);
        if (new Double(length) / maxSize > 0.75) {
//            UnifyLogger.warn(LogIdConst.INFO, false, "线程池队列堆积严重！");
        }
        return future;
    }

    @Override
    public void destroy() throws Exception {
        executor.shutdownNow();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = new MyThreadPoolExecutor(coreSize, maxSize, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(
                1000));
        Map<String, String> contMap = new HashMap<String, String>();
        contMap.put("核心线程数：", coreSize + "");
        contMap.put("最大线程数：", maxSize + "");
        contMap.put("缓冲队列大小：", 1000 + "");
//        UnifyLogger.info(LogIdConst.INFO, false, contMap);
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                UnifyLogger.error(LogIdConst.INFO, AlarmIdConst.CAR_PUSH_BATCH_PUSH_ERROR, false, "线程池已满！");
            }
        });
    }
}

class MyThreadPoolExecutor extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final AtomicLong numTasks = new AtomicLong(1);
    private final AtomicLong totalTime = new AtomicLong();

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            double avg = totalTime.get() / 1000000.0 / numTasks.get();
//            UnifyLogger.warn(LogIdConst.INFO, AlarmIdConst.CAR_PUSH_BATCH_PUSH_ERROR, false, "线程池关闭!", "平均执行时间：" + avg + "ms");
        } finally {
            super.terminated();
        }
    }

}
