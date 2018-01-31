package com.song.samples.concurrent.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class MergeSortTask extends RecursiveAction {

    private int[] array;

    private int low, high;

    public MergeSortTask(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (low < high) {
            // 分解为子任务
            int mid = (low + high) / 2;
            MergeSortTask leftTask = new MergeSortTask(array, low, mid);
            MergeSortTask rightTask = new MergeSortTask(array, mid + 1, high);

            // 执行子任务
            invokeAll(leftTask, rightTask);

            // 归并
            MergeSort.mergeArray(array, low, mid, high);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 100000000;
        Random random = new Random();
        int[] array = new int[n];
        for(int i = 0; i< n ;i++) {
            array[i] = random.nextInt(n);
        }

        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MergeSortTask mergeSortTask = new MergeSortTask(array, 0, array.length - 1);

        forkJoinPool.submit(mergeSortTask);

        forkJoinPool.awaitTermination(3, TimeUnit.SECONDS);

        forkJoinPool.shutdown();

        long end = System.currentTimeMillis();
//        for (int e : array) {
//            System.out.print(e + " ");
//            System.out.println();
//
//        }
        System.out.println("Time: " + (end - start));
    }
}
