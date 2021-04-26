package com.song.samples.multithread.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    /**
     * 获取产品价格的同步方法
     * @param product 产品名称
     * @return 产品价格
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        //一个模拟的延迟方法
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        int delay = 1000;
        //int delay = 500 + RANDOM.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    /**
     * 异步的获取产品价格
     *
     * @param product 产品名
     * @return 最终价格
     */
    public Future<Double> getPriceAsync(String product) {
        //创建CompletableFuture 对象，它会包含计算的结果
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        //在另一个线程中以异步方式执行计算
        new Thread(() -> {
            double price = calculatePrice(product);
            //需长时间计算的任务结 束并得出结果时，设置 Future的返回值
            futurePrice.complete(price);
        }).start();
        // 无需等待还没结束的计算，直接返回Future对象
        return futurePrice;
    }


}