package com.song.samples;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试公平锁与非公平锁的区别
 */
public class FairAndUnfairTest extends TestCase{

    CountDownLatch countDownLatch = new CountDownLatch(15);

    public static Lock fairLock = new ReentrantLock2(true);

    public static Lock unfairLock = new ReentrantLock2(false);

    /**
     * 自定义可重入锁，同步队列中的线程反向打印
     */
    private static class ReentrantLock2 extends ReentrantLock {

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> threadList = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(threadList);
            return threadList;
        }
    }

    public static class Job extends Thread{
        private Lock lock;

        private CountDownLatch countDownLatch;

        public Job(Lock lock, CountDownLatch countDownLatch) {
            this.lock = lock;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            countDownLatch.countDown();
            super.run();
            lock.lock();
            System.out.println(currentThread().getName());
            if (lock instanceof ReentrantLock2) {
                ReentrantLock2 lock2 = (ReentrantLock2) lock;
                System.out.println(lock2.getQueuedThreads());
            }
            lock.unlock();
        }
    }

    public void testApp() throws InterruptedException {
//        testLock(unfairLock, countDownLatch);
        testLock(fairLock, countDownLatch);
    }

    private void testLock(Lock lock, CountDownLatch countDownLatch) throws InterruptedException {
        for(int i = 0; i < 15;i++) {
            new Thread(new Job(lock, countDownLatch), "Thread-" + i).start();
        }
        countDownLatch.await();
    }
}
