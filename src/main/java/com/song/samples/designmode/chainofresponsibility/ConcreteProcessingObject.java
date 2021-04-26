package com.song.samples.designmode.chainofresponsibility;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 9:23 下午
 */

public class ConcreteProcessingObject {

    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();

        p1.setSuccessor(p2);

        String result = p1.handle("Aren't labdas really sexy?!!!");

        System.out.println(result);
    }
}
