package com.song.samples.multithread.Executor;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * future test
 */
public class FutureTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter start dictionary (e.g. /Users/songzeqi/workspace/): ");
        String dictionary = in.nextLine();
        System.out.println("Enter keyword (e.g. spring): ");
        String keyword = in.nextLine();

        ExecutorService pool = Executors.newCachedThreadPool();
        MatchCounter counter = new MatchCounter(new File(dictionary), keyword, pool);
        Future<Integer> result = pool.submit(counter);

        try {
            System.out.println(result.get() + "match files");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();

        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size=" + largestPoolSize);
    }
}

class MatchCounter implements Callable<Integer> {

    private File dictionary;
    private String keyword;
    private int count;
    private ExecutorService pool;

    public MatchCounter(File dictionary, String keyword, ExecutorService pool) {
        this.dictionary = dictionary;
        this.keyword = keyword;
        this.pool = pool;
    }

    public boolean search(File file) {
        try {
            boolean found = false;
            Scanner in = new Scanner(new FileInputStream(file));
            while (!found && in.hasNext()) {
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    found = true;
                }
            }
            in.close();
            return found;
        } catch (FileNotFoundException e) {
            return false;
        }

    }

    @Override
    public Integer call() throws Exception {
        count = 0;
        try {
            File[] files = dictionary.listFiles();
            ArrayList<Future<Integer>> results = Lists.newArrayList();
            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword, pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);
                } else {
                    if (search(file)) {
                        count++;
                    }
                }
            }

            for (Future<Integer> future : results) {
                count += future.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return count;
    }
}