package com.song.samples.collections;

import java.util.BitSet;

/**
 * Eratosthenes
 * compute primes
 */
public class Sieve {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int n = 2000000;
        int count = 0;
        BitSet bs = new BitSet(n + 1);
        for(int i = 2; i <= n; i++) {
            bs.set(i);
        }

        int i = 2;
        while (i * i <= n) {
            if (bs.get(i)) {
                count++;
                int k = 2*i;
                while (k <= n) {
                    bs.clear(k);
                    k += i;
                }
            }
            i++;
        }

        while (i <= n) {
            if (bs.get(i)) {
                count++;
            }
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " primes.");
        System.out.println("times: " + (end - start));
    }
}
