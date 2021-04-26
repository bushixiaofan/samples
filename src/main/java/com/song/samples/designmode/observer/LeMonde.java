package com.song.samples.designmode.observer;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 8:02 下午
 */

public class LeMonde implements Observer<String> {
    @Override
    public void notify(String tweet) {
        if (tweet != null && tweet.contains("wine")) {
            System.out.println("Today cheese, wine and news! " + tweet);
        }
    }
}
