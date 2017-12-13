package com.song.samples.multithread.BlockQueue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * blocking queue
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter start dictionary (e.g. /Users/songzeqi/workspace/): ");
        String dictionary = in.nextLine();
        System.out.println("Enter keyword (e.g. spring): ");
        String keyword = in.nextLine();

        final int FILE_QUEUE_SIZE = 10;
        final int SEARCH_THREAD_SIZE = 100;

        // 构造时需要制定容量，并且又一个可选参数指定是否需要公平性
        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);

        FileEnumerationTask enumerationTask = new FileEnumerationTask(queue, new File(dictionary));
        new Thread(enumerationTask).start();
        for(int i = 0; i< SEARCH_THREAD_SIZE; i++) {
            new Thread(new SearchTask(queue, keyword)).start();
        }
    }

}

/**
 * this task enumerate files from start dictionary and sub dictionary
 */
class FileEnumerationTask implements Runnable {

    public static File DUMMY = new File("");
    private File startDictionary;
    private BlockingQueue<File> queue;

    public FileEnumerationTask(BlockingQueue<File> queue, File startDictionary) {
        this.startDictionary = startDictionary;
        this.queue = queue;
    }

    public void enumerate(File startDictionary) throws InterruptedException {
        File[] files = startDictionary.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                enumerate(file);
            } else {
                queue.put(file);
            }
        }
    }

    @Override
    public void run() {
        try {
            enumerate(startDictionary);
            queue.put(DUMMY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * search file for a given keyword
 */
class SearchTask implements Runnable {

    private BlockingQueue<File> queue;
    private String keyWord;

    public SearchTask(BlockingQueue<File> queue, String keyWord) {
        this.queue = queue;
        this.keyWord = keyWord;
    }

    public void search(File file) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream(file));
        int lineNumber = 0;
        while (in.hasNext()) {
            lineNumber++;
            String line = in.nextLine();
            if (line.contains(keyWord)) {
                System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
            }
        }
        in.close();
    }

    @Override
    public void run() {
        try {
            boolean done = false;
            while (!done) {
                // 移出并返回头元素，如果队列为空，则阻塞
                File file = queue.take();
                if (file == FileEnumerationTask.DUMMY) {
                    // 添加一个元素，队列满，则阻塞
                    queue.put(file);
                    done = true;
                } else {
                    search(file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
