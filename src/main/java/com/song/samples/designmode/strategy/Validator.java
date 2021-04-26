package com.song.samples.designmode.strategy;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 4:58 下午
 */

public class Validator {

    private ValidationStrategy<String> strategy;

    public Validator(ValidationStrategy<String> strategy) {
        this.strategy = strategy;
    }

    public boolean validate(String s) {
        return strategy.validate(s);
    }

    public static void main(String[] args) {
        Validator numericValidator = new Validator(new IsNumeric());
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean b1 = numericValidator.validate("aaaa");
        boolean b2 = lowerCaseValidator.validate("bbbb");
    }
}
