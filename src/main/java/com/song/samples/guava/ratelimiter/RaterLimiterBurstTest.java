package com.song.samples.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

/**
 * @author: songzeqi
 * @Date: 2019-09-18 8:44 PM
 */

public class RaterLimiterBurstTest {
    public static void main(String[] args) {
        Long cur = System.currentTimeMillis();
        RateLimiter rateLimiter = RateLimiter.create(10);
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        System.out.println(rateLimiter.acquire(10));
        System.out.println("past: " + (System.currentTimeMillis() - cur)  );
        for (int i = 1; i <= 20; i++) {
            System.out.println(rateLimiter.acquire());
            System.out.println("past: " + (System.currentTimeMillis() - cur)  );
        }

        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        System.out.println(rateLimiter.acquire(100));
        System.out.println("past: " + (System.currentTimeMillis() - cur)  );
        for (int i = 1; i <= 100; i++) {
            System.out.println(rateLimiter.acquire());
            System.out.println("past: " + (System.currentTimeMillis() - cur) );
        }
    }
}
