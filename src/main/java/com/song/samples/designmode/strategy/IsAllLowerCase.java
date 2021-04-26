package com.song.samples.designmode.strategy;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 4:42 下午
 */

public class IsAllLowerCase implements ValidationStrategy<String> {

    @Override
    public boolean validate(String o) {
        return o.matches("[a-z]+");
    }
}
