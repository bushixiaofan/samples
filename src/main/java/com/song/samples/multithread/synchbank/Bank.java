package com.song.samples.multithread.synchbank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a bank with a number of account
 */
public class Bank {
    private final double[] accounts;
    // 保护临界区的可重入锁
    private Lock bankLock;
    // 与锁相关的条件对象（可以创建一个或者多个）
    private Condition sufficientFounds;

    public Bank(int accountNum, double initinalBalance) {
        accounts = new double[accountNum];
        for (int i = 0; i < accountNum; i++) {
            accounts[i] = initinalBalance;
        }
        bankLock = new ReentrantLock();
        sufficientFounds = bankLock.newCondition();
    }

    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try {
            if (accounts[from] < amount) {
                // 将改线程放到条件等待集中
                sufficientFounds.await();
            }

            System.out.println(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf("%10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf("total balance %10.2f", getTotalBalance());

            // 解除改条件等待集中所有线程的等待状态
            sufficientFounds.signalAll();
        } finally {
            bankLock.unlock();
        }

    }

    public double getTotalBalance() {
        bankLock.lock();
        try {
            double sum = 0;
            for (double account : accounts) {
                sum += account;
            }
            return sum;
        } finally {
            bankLock.unlock();
        }

    }

    public int size() {
        return accounts.length;
    }
}
