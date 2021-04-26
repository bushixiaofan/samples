package com.song.samples.designmode.chainofresponsibility;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 9:20 下午
 */

public class HeaderTextProcessing extends ProcessingObject<String> {

    @Override
    protected String handleWork(String input) {
        return "From Raoul, Mario and Alan: " + input;
    }
}
