package com.song.samples.designmode.factory;

/**
 * @author: songzeqi
 * @Date: 2021-04-26 10:03 下午
 */

public class ProductFactory {

    public static Product createProduct(String name) {
        switch (name) {
            case "loan":
                return new Loan();
            case "stock":
                return new Stock();
            case "bond":
                return new Bond();
            default:
                throw new RuntimeException("No such product " + name);
        }
    }

    public static void main(String[] args) {
        Product p = ProductFactory.createProduct("loan");
    }
}
