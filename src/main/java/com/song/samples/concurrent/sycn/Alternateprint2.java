package com.song.samples.concurrent.sycn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Alternateprint2 {

    private int i = 0;

    private boolean flag = true;
    private Lock lock = new ReentrantLock();
    private Condition qiCondition = lock.newCondition();
    private Condition ouCondition = lock.newCondition();


    /**
     * 打印奇数
     */
    private void PrintQi() throws InterruptedException {
        String threadName = Thread.currentThread().getName();

        while (i < 100) {
            lock.lock();
            if (flag) {
                ouCondition.await();
            }
            System.out.println(threadName + " " + String.valueOf(flag) + " " + i);
            i++;
            flag = true;
            qiCondition.signal();
            lock.unlock();
        }
    }

    /**
     * 打印偶数
     */
    private void PrintOu() throws InterruptedException {
        String threadName = Thread.currentThread().getName();

        while (i < 100) {
            lock.lock();
            if (!flag) {
                qiCondition.await();
            }
            System.out.println(threadName + " " + String.valueOf(flag) + " " + i);
            i++;
            flag = false;
            ouCondition.signal();
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        final Alternateprint2 data = new Alternateprint2();

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    data.PrintQi();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread_1").start();


        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    data.PrintOu();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread_2").start();
    }
}
