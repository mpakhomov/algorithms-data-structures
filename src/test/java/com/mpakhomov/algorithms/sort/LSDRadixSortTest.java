package com.mpakhomov.algorithms.sort;

import com.mpakhomov.seq.Sequence;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author mpakhomov
 * @since 11/22/15
 */
public class LSDRadixSortTest {

    @Test
    public void testSortSimple() {
        int[] a = new int[] {10000000, 12, 0, 14, 77, 42, 4, 1};
        LSDSortFor32BitIntegers sorter = new LSDSortFor32BitIntegers();
        sorter.sort(a);
        assertThat(Sequence.isSorted(a), is(true));
    }

    @Test
    public void testSortOneMillion32BitIntegers() {
        final int N = 1_000_000;
        int[] a = new int[N];
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            a[i] = random.nextInt(Integer.MAX_VALUE);
        }

        LSDSortFor32BitIntegers sorter = new LSDSortFor32BitIntegers();
        long begin = System.currentTimeMillis();
        sorter.sort(a);
        long end =  System.currentTimeMillis();
//        System.out.println("It took: " + (end - begin) + " ms");
        assertThat(Sequence.isSorted(a), is(true));
    }
}
