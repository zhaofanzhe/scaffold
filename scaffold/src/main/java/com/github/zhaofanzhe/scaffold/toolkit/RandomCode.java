package com.github.zhaofanzhe.scaffold.toolkit;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomCode {

    public static String nextCode(int length) {
        final Random random = new Random(System.currentTimeMillis());
        return IntStream.range(0, length)
                .mapToObj(value -> Integer.toString(random.nextInt(10)))
                .collect(Collectors.joining(""));
    }

}
