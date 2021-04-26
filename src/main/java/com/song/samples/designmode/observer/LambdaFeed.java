package com.song.samples.designmode.observer;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 8:46 下午
 */

public class LambdaFeed {

    public static void main(String[] args) {
        Feed f = new Feed();
        f.registerObservers(tweet -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking new in NY! " + tweet);
            }
        });

        f.registerObservers(tweet -> {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        });

        f.registerObservers(tweet -> {
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("Today cheese, wine and news! " + tweet);
            }
        });

        f.notifyObservers("The queen said her favourite bool is Java 8 in Action!");
    }
}
