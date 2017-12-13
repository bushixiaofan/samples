package com.song.samples.multithread.future;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

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

        MatchCounter counter = new MatchCounter(new File(dictionary), keyword);
        FutureTask<Integer> task = new FutureTask<Integer>(counter);
        new Thread(task).start();

        try {
            System.out.println(task.get() + " match files");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}

class MatchCounter implements Callable<Integer> {

    private File dictionary;
    private String keyword;
    private int count;

    public MatchCounter(File dictionary, String keyword) {
        this.dictionary = dictionary;
        this.keyword = keyword;
    }

    public boolean search(File file, String keyword) {
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
            ArrayList<Future<Integer>> result = Lists.newArrayList();
            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword);
                    FutureTask<Integer> task = new FutureTask<Integer>(counter);
                    result.add(task);
                    Thread t = new Thread(task);
                    t.start();
                } else {
                    if (search(file, keyword)) {
                        count++;
                    }
                }
            }

            for (Future<Integer> future : result) {
                count += future.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return count;
    }
}