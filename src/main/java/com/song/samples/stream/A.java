package com.song.samples.stream;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class A {
    @Getter
    private String a;

    @Getter
    private Integer b;

    public A(String a, Integer b) {
        this.a = a;
        this.b = b;
    }

    public static void main(String[] args) {
        List<Integer> ret = Lists.newArrayList(new A("a", 1), new A("b", 2), new A("c", 3)).stream()
                .map(A::getB)
                .filter(b -> b>=2)
                .collect(Collectors.toList());
        System.out.println(ret);
    }
}

