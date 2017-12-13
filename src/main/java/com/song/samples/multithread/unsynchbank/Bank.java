package com.song.samples.multithread.unsynchbank;

/**
 * a bank with a number of account
 */
public class Bank {
    private final double[] accounts;

    public Bank(int accountNum, double initinalBalance) {
        accounts = new double[accountNum];
        for(int i = 0; i < accountNum; i++) {
            accounts[i] = initinalBalance;
        }
    }

    public void transfer(int from, int to, double amount) {
        if (accounts[from] < amount) {
            return;
        }

        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf("%10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf("total balance %10.2f", getTotalBalance());
        System.out.println();

    }

    public double getTotalBalance() {
        double sum = 0;
        for (double account : accounts) {
            sum += account;
        }
        return sum;
    }

    public int size() {
        return accounts.length;
    }
}
