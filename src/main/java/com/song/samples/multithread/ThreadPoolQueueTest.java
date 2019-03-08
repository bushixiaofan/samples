package com.song.samples.multithread;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;
import com.song.samples.Utils.DateUtils;
import com.song.samples.multithread.Utils.ThreadPoolUtil;

/**
 * @author: songzeqi
 * @Date: 2019-03-08 11:30 AM
 */

public class ThreadPoolQueueTest {

    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        ListeningExecutorService executorService = ThreadPoolUtil.getLecheckBatchDeductExecutor();
        RateLimiter rateLimiter = RateLimiter.create(30);
        Task task = new Task();
        List<ListenableFuture<Boolean>> listenableFutures = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 200; j++) {
                rateLimiter.acquire();
                listenableFutures.add(executorService.submit(task));
            }

            for (ListenableFuture<Boolean> listenableFuture : listenableFutures) {
                try {
                    listenableFuture.get();
                } catch (ExecutionException | InterruptedException e) {
                    System.err.println("执行失败");
                }
            }
        }
    }

    public static class Task implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            counter.getAndIncrement();
            Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);
            System.out.println("System.currentTimeMillis = " + DateUtils.formatDateTime(new Date())
                    + ": 模拟操作完成，current thread no = " + Thread.currentThread().getName() + ", 执行总数 = " + counter);
            return true;
        }
    }
}
