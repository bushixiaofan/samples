package com.song.samples.designmode.observer;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 8:07 下午
 */

public class Feed implements Subject<String> {

    private final List<Observer<String>> observers = Lists.newArrayList();

    @Override
    public void registerObservers(Observer<String> observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(String tweet) {
        observers.forEach(o-> o.notify(tweet));
    }

    public static void main(String[] args) {
        Feed f = new Feed();
        f.registerObservers(new NYTimes());
        f.registerObservers(new Guardian());
        f.registerObservers(new LeMonde());
        f.notifyObservers("The queen said her favourite bool is Java 8 in Action!");
    }
}
