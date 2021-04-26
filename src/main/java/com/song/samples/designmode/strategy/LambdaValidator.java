package com.song.samples.designmode.strategy;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 5:05 下午
 */

public class LambdaValidator {
    public static void main(String[] args) {
        Validator numericValidator = new Validator(s -> s.matches("[a-z]+"));
        Validator lowerCaseValidator = new Validator(s -> s.matches("\\d+"));
    }
}
