package com.mpakhomov.algorithms.misc;

public class Fibonacci {

    /**
     * find minimum fibonacci number greater than the given number `n`
     * @param n given number
     * @return
     */
    public static int fibonacci(int n) {
        if (n <= 0) return 1;
        int prevPrev = 1;
        int prev = 1;
        int cur = 2;
        while(cur <= n) {
            prevPrev = prev;
            prev = cur;
            cur = prevPrev + prev;
        }
        return cur;
    }
}
