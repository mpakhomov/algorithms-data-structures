package com.mpakhomov.algorithms.misc;

import java.util.ArrayList;
import java.util.List;

public class SieveOfEratosthenes {

    public static List<Integer> sieve(int n) {
        boolean[] primes = new boolean[n + 1];

        // initialize all to true, except 0, 1
        for (int i = 2; i <= n; i ++) primes[i] = true;

        for (int factor = 2; factor * factor <= n; factor++) {
            // skip until the next prime
           if (!primes[factor]) continue;

            // factor is a prime, now let's cross out all numbers divisible by factor
            for (int i = factor + factor; i <= n; i += factor)
                primes[i] = false;
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 2; i <= n; i++)
            if (primes[i]) results.add(i);
        return results;
    }
}
