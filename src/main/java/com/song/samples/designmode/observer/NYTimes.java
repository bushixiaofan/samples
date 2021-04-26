package com.song.samples.designmode.observer;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 7:59 下午
 */

public class NYTimes implements Observer<String>{
    @Override
    public void notify(String tweet) {
        if (tweet != null && tweet.contains("money")) {
            System.out.println("Breaking news in NY!" + tweet);
        }
    }
}
