package com.song.samples.multithread;

import java.util.Date;
import java.util.List;
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
 * @Date: 2019-04-08 11:36 AM
 */

public class ThreadPoolAtomicTest {

    public static void main(String[] args) {
        ListeningExecutorService executorService = ThreadPoolUtil.getLecheckBatchDeductExecutor();
        RateLimiter rateLimiter = RateLimiter.create(30);
        AtomicInteger counter = new AtomicInteger(0);

        List<ListenableFuture<Boolean>> listenableFutures = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                rateLimiter.acquire();
                listenableFutures.add(executorService.submit(() -> doSomeThing(counter)));
            }

            for (ListenableFuture<Boolean> listenableFuture : listenableFutures) {
                try {
                    listenableFuture.get();
                } catch (ExecutionException | InterruptedException e) {
                    System.err.println("执行失败");
                }
            }
        }
        System.out.println("操作最终完成");
    }

    private static boolean doSomeThing(AtomicInteger counter) {
        counter.getAndIncrement();
        Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);
        System.out.println("System.currentTimeMillis = " + DateUtils.formatDateTime(new Date())
                + ": 模拟操作完成，current thread no = " + Thread.currentThread().getName() + ", 执行总数 = " + counter);
        return true;
    }
}
