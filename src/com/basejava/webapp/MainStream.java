package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    private static final int[] NUMS_1 = new int[]{1, 2, 3, 3, 2, 3};
    private static final int[] NUMS_2 = new int[]{9, 8};

    private static final List<Integer> NUMS_LIST_1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private static final List<Integer> NUMS_LIST_2 = Arrays.asList(1, 2, 3, 4, 5, 8, 9);

    public static void main(String[] args) {
        System.out.println(minValue(NUMS_1));
        System.out.println(minValue(NUMS_2));
        System.out.println(oddOrEven(NUMS_LIST_1));
        System.out.println(oddOrEven(NUMS_LIST_2));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (left, right) -> left * 10 + right);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
/*
        final int oddOrEvenSum = integers.stream().mapToInt(Integer::intValue).sum() % 2;
        return integers
                .stream()
                .filter(num -> num % 2 != oddOrEvenSum)
                .collect(Collectors.toList());

 */
        return integers
                .stream()
                .filter(integers.stream().mapToInt(Integer::intValue).sum() % 2 == 0 ? num -> num % 2 != 0 : num -> num % 2 == 0)
                .collect(Collectors.toList());

    }

}
