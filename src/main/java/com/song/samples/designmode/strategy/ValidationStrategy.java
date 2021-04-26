package com.song.samples.designmode.strategy;

public interface ValidationStrategy <T> {
    boolean validate(T o);
}
