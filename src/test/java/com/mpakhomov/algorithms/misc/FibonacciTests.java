package com.mpakhomov.algorithms.misc;

import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FibonacciTests {

    @Test
    public void test0() {
        int actual = Fibonacci.fibonacci(0);
        assertThat(actual, is(1));
    }

    @Test
    public void test1() {
        int actual = Fibonacci.fibonacci(1);
        assertThat(actual, is(2));
    }

    @Test
    public void test2() {
        int actual = Fibonacci.fibonacci(2);
        assertThat(actual, is(3));
    }

    @Test
    public void test4() {
        int actual = Fibonacci.fibonacci(4);
        assertThat(actual, is(5));
    }

    @Test
    public void test5() {
        int actual = Fibonacci.fibonacci(5);
        assertThat(actual, is(8));
    }

    @Test
    public void test6() {
        int actual = Fibonacci.fibonacci(6);
        assertThat(actual, is(8));
    }

    @Test
    public void test8() {
        int actual = Fibonacci.fibonacci(8);
        assertThat(actual, is(13));
    }

    @Test
    public void test11() {
        int actual = Fibonacci.fibonacci(11);
        assertThat(actual, is(13));
    }

    @Test
    public void test13() {
        int actual = Fibonacci.fibonacci(13);
        assertThat(actual, is(21));
    }

    @Test
    public void test37() {
        int actual = Fibonacci.fibonacci(37);
        assertThat(actual, is(55));
    }

}
