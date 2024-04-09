package com.song.samples.lambda;

import java.util.function.Supplier;

/**
 * @Author songzeqi
 * @create 2024/4/3 18:45
 * Description:
 */
public class LazyList<T> implements MyList<T> {

    private final T head;

    private final Supplier<MyList<T>> tail;

    public LazyList(T head, Supplier<MyList<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public MyList<T> tail() {
        return tail.get();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
