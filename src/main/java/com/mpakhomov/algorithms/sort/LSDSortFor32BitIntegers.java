package com.mpakhomov.algorithms.sort;

import java.util.Objects;

/**
 * LSD Radix sort for 32-bit integers
 *
 * @author mpakhomov
 * @since 11/22/15
 */
public class LSDSortFor32BitIntegers {
    /**
     * Breaks up 32-bit integer into 8-bit chunks (unsigned bytes), i.e each chunk is an integer within [0, 255]
     * <p>
     * Examples:
     * <p>
     * {@code byteToHexString(getByte(0xFF123456, 0) == 0xFF}
     * {@code byteToHexString(getByte(0xFF123456, 1) == 0x12}
     * {@code byteToHexString(getByte(0xFF123456, 2) == 0x34}
     * {@code byteToHexString(getByte(0xFF123456, 2) == 0x56}
     *
     *
     * @param n   32-bit integer
     * @param pos get byte at the given pos. pos == 0 gives the highest byte,
     *            pos == 3 gives the lowest byte
     * @return byte
     */
    public static int getByte(int n, int pos) {
        switch (pos) {
            case 0:
                return (n >>> 24) & 0xFF;
            case 1:
                return (n >>> 16) & 0xFF;
            case 2:
                return (n >>> 8) & 0xFF;
            case 3:
                return n & 0xFF;
            default:
                throw new IllegalArgumentException("pos should be in [0, 3]");
        }
    }

    /**
     * converts byte to a string of hexadecimal representation of n. pads with leading zeroes when
     * necessary
     *
     * @param n byte
     * @return string representation of n in hex format
     */
    public static String byteToHexString(int n) {
        return String.format("0x%02X", n);
    }

    /**
     * converts int to a string of hexadecimal representation of n. pads with leading zeroes when
     * necessary
     *
     * @param n 32-bit integer
     * @return string representation of n in hex format
     */
    public static String intToHexString(int n) {
        return String.format("0x%08X", n);
    }

    /**
     * sorts an array of 32-bit integers
     * @param a
     */
    public void sort(int[] a) {
        final int radix = 1 << 8; //256, 2 ^ 8
        final int N = a.length;
        int[] aux = new int[N]; // auxiliary array

        Objects.requireNonNull(a);
        // empty array and a singleton array is already sorted
        if (a.length < 2) {
            return;
        }

        // LSD starts with least significant byte
        for (int pos = 3; pos >= 0; pos--) {
            int[] count = new int[radix + 1];

            // count frequencies
            for (int i : a) {
                count[getByte(i, pos) + 1]++;
            }

            for (int r = 0; r < radix; r++) {
                count[r + 1] += count[r];
            }

            for (int i : a) {
                aux[count[getByte(i, pos)]++] = i;
            }

            // copy back
            System.arraycopy(aux, 0, a, 0, N);
        }
    }
}
