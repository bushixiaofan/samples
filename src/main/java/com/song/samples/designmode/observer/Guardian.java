package com.song.samples.designmode.observer;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 8:01 下午
 */

public class Guardian implements Observer<String> {
    @Override
    public void notify(String tweet) {
        if (tweet != null && tweet.contains("queen")) {
            System.out.println("Yet another news in London... " + tweet);
        }
    }
}
