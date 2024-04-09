package com.song.samples.lambda;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author songzeqi
 * @create 2024/4/3 17:28
 * Description:
 */
public class LambdaTest {
    static DoubleUnaryOperator curriedConverter(double f, double b) {
        return (double x) -> x * f + b;
    }

    public static void test1() {
        DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
        DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
        DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);
        System.out.println(convertCtoF.applyAsDouble(100));
    }

    public static Stream<Integer> primes(int n) {
        return Stream.iterate(2, i -> i + 1)
                .filter(LambdaTest::isPrime)
                .limit(n);
    }


    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    static IntStream numbers() {
        return IntStream.iterate(2, n -> n + 1);
    }

    static int head(IntStream numbers) {
        return numbers.findFirst().getAsInt();
    }

    static IntStream tail(IntStream numbers) {
        return numbers.skip(1);
    }

    static IntStream primes1(IntStream numbers) {
        int head = head(numbers);
        return IntStream.concat(
                IntStream.of(head),
                primes1(tail(numbers).filter(n -> n % head != 0))
        );
    }

    public static void test2() {
        IntStream primes = primes1(IntStream.rangeClosed(2, 100));
    }

    public static LazyList<Integer> from(int n) {
        return new LazyList<>(n, () -> from(n + 1));
    }

    public static void test3() {
        LazyList<Integer> numbers = from(2);
        int two = numbers.head();
        int three = numbers.tail().head();
        int four = numbers.tail().tail().head();

        System.out.println(two + " " + three + " " + four);
    }

    public static MyList<Integer> primes2(MyList<Integer> numbers) {
        return new LazyList<>(
                numbers.head(),
                () -> primes2(
                        numbers.tail()
                                .filter(n -> n % numbers.head() != 0)
                )
        );
    }

    static <T> void printAll(MyList<T> list) {
        while (!list.isEmpty()) {
            System.out.println(list.head());
            list = list.tail();
        }
    }

    public static void test4() {
        LazyList<Integer> numbers = from(2);
        int two = primes2(numbers).head();
        int three = primes2(numbers).tail().head();
        int five = primes2(numbers).tail().tail().head();
        int seven = primes2(numbers).tail().tail().tail().head();

        System.out.println(two + " " + three + " " + five + " " + seven);
//        printAll(primes2(from(2)));
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }
}
