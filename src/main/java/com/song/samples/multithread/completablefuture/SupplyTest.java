package com.song.samples.multithread.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * @author: songzeqi
 * @Date: 2019-07-25 11:04 AM
 */

public class SupplyTest {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> 1).thenApply(i -> i + 1).thenApply(i -> i * i).whenComplete((r, e) -> {
            System.out.println(r);
            System.out.println(e);
        });
    }
}
