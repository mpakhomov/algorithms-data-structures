package com.mpakhomov;

import java.util.Random;

/**
 * @author mpakhomov
 * @since 8/24/2015
 */
public class TestUtil {

    static public int[] generateRandomIntArray(int size, int low, int high) {
        return new Random().ints(size, low, high).toArray();
    }
}
