package com.mpakhomov.algorithms.misc;

import org.junit.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SieveOfEratosthenesTests {

    @Test
    public void test100() {
        List<Integer> actual = SieveOfEratosthenes.sieve(100);
//        System.out.println(actual.stream().map(i -> i.toString()).collect(Collectors.joining(", ")));
        List<Integer> expected = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97);
        assertThat(actual, is(expected));
    }
}
