package com.song.samples.guava.ratelimiter;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;

/**
 * @author: songzeqi
 * @Date: 2019-04-11 4:51 PM
 */

public class RateLimiterTest {

    public static void main(String[] args) {
        Long cur = System.currentTimeMillis();
        RateLimiter rateLimiter = RateLimiter.create(10);
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        for (int i = 1; i <= 50; i++) {
            System.out.println(i + ": " + rateLimiter.acquire());
            System.out.println(i + ": " + "past: " + (System.currentTimeMillis() - cur)  );
        }
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        for (int i = 1; i <= 50; i++) {
            System.out.println(i + ": " + rateLimiter.acquire());
            System.out.println(i + ": " + "past: " + (System.currentTimeMillis() - cur) );
        }
    }
}
