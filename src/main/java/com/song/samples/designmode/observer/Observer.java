package com.song.samples.designmode.observer;

public interface Observer<T> {
    void notify(T tweet);
}
