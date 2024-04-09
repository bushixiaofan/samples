package com.song.samples.lambda;

/**
 * @Author songzeqi
 * @create 2024/4/8 15:16
 * Description:
 */
public interface TriFunction<S, T, U, R> {

    R apply(S s, T t, U u);

}
