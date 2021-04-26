package com.song.samples.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author: songzeqi
 * @Date: 2019-11-12 12:00 PM
 */

public class RateLimiterWarmupTest {
    public static void main(String[] args) {
        Long cur = System.currentTimeMillis();
        RateLimiter rateLimiter = RateLimiter.create(10, 10, TimeUnit.SECONDS);
        System.out.println(rateLimiter.acquire(150));
        System.out.println("past: " + (System.currentTimeMillis() - cur)  );
        for (int i = 1; i <= 2000; i++) {
            System.out.println(rateLimiter.acquire());
            System.out.println("past: " + (System.currentTimeMillis() - cur)  );
        }
    }
}
