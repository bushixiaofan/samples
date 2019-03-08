package com.song.samples.multithread.Utils;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * gaohaiyang
 *
 *
 */
public class ThreadPoolUtil {
    /**
     * 此executor由收银台展示判断请求专用
     */
    private static ThreadPoolExecutor installmentAvailableExecutor = new ThreadPoolExecutor(30, 100, 60L,
            TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new DefaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutor lecheckBatchDeductExecutor = new ThreadPoolExecutor(100, 500, 60L,
            TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new DefaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static ListeningExecutorService getLecheckBatchDeductExecutor() {
        return MoreExecutors.listeningDecorator(lecheckBatchDeductExecutor);
    }

    public static ListeningExecutorService getInstallmentsAvailabeListeningExecutor() {
        return MoreExecutors.listeningDecorator(installmentAvailableExecutor);
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "biz-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
