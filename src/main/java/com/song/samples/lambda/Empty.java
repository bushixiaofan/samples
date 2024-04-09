package com.song.samples.lambda;

/**
 * @Author songzeqi
 * @create 2024/4/3 18:44
 * Description:
 */
public class Empty<T> implements MyList<T>{
    @Override
    public T head() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MyList<T> tail() {
        throw new UnsupportedOperationException();
    }
}
