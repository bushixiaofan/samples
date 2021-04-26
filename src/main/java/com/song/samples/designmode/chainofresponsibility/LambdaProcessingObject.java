package com.song.samples.designmode.chainofresponsibility;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 9:39 下午
 */

public class LambdaProcessingObject {
    public static void main(String[] args) {
        UnaryOperator<String> headerProcessing = text -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing = text -> text.replaceAll("labda", "lambda");

        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);

        String result = pipeline.apply("Aren't labda really sexy?!!");
        System.out.println(result);
    }
}
