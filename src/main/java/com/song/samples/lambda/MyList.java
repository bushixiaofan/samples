package com.song.samples.lambda;

import java.util.function.Predicate;

/**
 * @Author songzeqi
 * @create 2024/4/3 18:40
 * Description:
 */
public interface MyList<T> {
    T head();

    MyList<T> tail();

    default boolean isEmpty() {
        return true;
    }

    default MyList<T> filter(Predicate<T> p) {
        return isEmpty() ? this : p.test(head()) ? new LazyList<>(head(), () -> tail().filter(p)) : tail().filter(p);
    }
}
