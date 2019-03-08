package com.song.samples.multithread;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: songzeqi
 * @Date: 2019-03-08 4:18 PM
 */

public class ThreadPoolRejectTest {
    private static AtomicInteger counter = new AtomicInteger(0);
    private static AtomicInteger qps = new AtomicInteger(2000);


    public static void main(String[] args) {
        final RateLimiter rateLimiter = RateLimiter.create(2000);
        Task task = new Task();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                int i = qps.addAndGet(-10);
                rateLimiter.setRate(i);
                System.out.println("qps下降到"+i);
            }
        });
        for (;;) {
            rateLimiter.acquire();
            executor.submit(task);
        }
    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            Uninterruptibles.sleepUninterruptibly(10,TimeUnit.MILLISECONDS);
        }
    }
}
