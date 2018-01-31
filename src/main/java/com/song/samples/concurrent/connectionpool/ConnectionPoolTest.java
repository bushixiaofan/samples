package com.song.samples.concurrent.connectionpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {

    // 初始化连接池
    static ConnectionPool pool = new ConnectionPool(10);
    // 保证所有的ConnectionRunner能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // main 线程等待所有ConnectionRunner结束后才继续执行
    static CountDownLatch end;
    // 记录获取连接成功次数
    static AtomicInteger got = new AtomicInteger();
    // 记录获取连接失败次数
    static AtomicInteger notGot = new AtomicInteger();

    static class ConnectionRunner implements Runnable {
        // 该线程尝试获取连接次数
        int count;

        public ConnectionRunner(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            try {
                // 等待所有线程创建完毕
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (count > 0) {
                try {
                    Connection connection = pool.fetchConnection(100);
                    if (connection == null) {
                        notGot.incrementAndGet();
                    } else {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    count--;
                }
            }
            end.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 100;
        end = new CountDownLatch(threadCount);
        int count = 200;
        for (int i = 0; i < threadCount; i++) {
            new Thread(new ConnectionRunner(count)).start();
        }

        start.countDown();
        end.await();

        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection: " + notGot);
    }
}
