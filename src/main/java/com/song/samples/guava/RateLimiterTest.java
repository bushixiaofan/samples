package com.song.samples.guava;

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
        rateLimiter.acquire();
        for (int i = 1; i <= 100; i++) {
            rateLimiter.acquire();
            System.out.println("past: " + (System.currentTimeMillis() - cur)  );
        }
        Uninterruptibles.sleepUninterruptibly(1

                , TimeUnit.SECONDS);
        rateLimiter.acquire();
        for (int i = 1; i <= 100; i++) {
            rateLimiter.acquire();
            System.out.println("past: " + (System.currentTimeMillis() - cur) );
        }
    }
}
