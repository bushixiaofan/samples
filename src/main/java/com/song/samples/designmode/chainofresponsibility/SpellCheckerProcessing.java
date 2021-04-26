package com.song.samples.designmode.chainofresponsibility;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 9:22 下午
 */

public class SpellCheckerProcessing extends ProcessingObject<String> {
    @Override
    protected String handleWork(String input) {
        return input.replaceAll("labda", "lambda");
    }
}
