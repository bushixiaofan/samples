package com.song.samples.designmode.observer;

public interface Subject<T> {
    void registerObservers(Observer<T> observer);

    void notifyObservers(T tweet);
}
