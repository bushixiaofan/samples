package com.song.samples.concurrent.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * 归并排序实现
 */
public class MergeSort {

    public static void mergeArray(int[] array, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = 0;
        int first = low;
        int m = mid;
        int second = mid + 1;
        int n = high;
        while (first <= m && second <= n) {
            if (array[first] > array[second]) {
                temp[i++] = array[first++];
            } else {
                temp[i++] = array[second++];
            }
        }
        while (first <= m) {
            temp[i++] = array[first++];
        }

        while (second <= n) {
            temp[i++] = array[second++];
        }

        for (int k = 0; k < i; k++) {
            array[low + k] = temp[k];
        }
    }

    public static void mergeSort(int[] array, int low, int high) {
        if (high > low) {
            int mid = (low + high) / 2;
            mergeSort(array, low, mid);
            mergeSort(array, mid + 1, high);
            mergeArray(array, low, mid, high);
        }
    }

    public static void main(String[] args) {

        int n = 100000000;
        Random random = new Random();
        int[] array = new int[n];
        for(int i = 0; i< n ;i++) {
            array[i] = random.nextInt(n);
        }

        long start = System.currentTimeMillis();
        mergeSort(array, 0, array.length - 1);

        long end = System.currentTimeMillis();
//        for (int e : array) {
//            System.out.print(e + " ");
//            System.out.println();
//
//        }
        System.out.println("Time: " + (end - start));
    }
}
