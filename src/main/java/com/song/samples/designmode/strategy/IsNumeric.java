package com.song.samples.designmode.strategy;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 4:52 下午
 */

public class IsNumeric implements ValidationStrategy<String> {
    @Override
    public boolean validate(String o) {
        return o.matches("\\d+");
    }
}
