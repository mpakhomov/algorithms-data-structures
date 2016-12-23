package com.mpakhomov.algorithms.sort;

import com.mpakhomov.TestUtil;
import com.mpakhomov.seq.Sequence;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SortingTests {

    private final static Sorting[] sorters = new Sorting[] {
            new BubbleSort(), new InsertionSort(), new LSDSortFor32BitIntegers(), new MergeSort(),
            new SelectionSort()
    };

    @Test
    public void test3725() {
        int[] a = new int[] {3, 7, 2, 5};
        for (Sorting sorter : sorters) {
            sorter.instanceSort(a);
            assertTrue(sorter.getClass().getSimpleName(), Sequence.isSorted(a));
        }
    }

    @Test
    public void testSortedAsc() {
        int[] sorted = new int[] {1, 2, 3, 4};
        for (Sorting sorter : sorters) {
            sorter.instanceSort(sorted);
            assertTrue(sorter.getClass().getSimpleName(), Sequence.isSorted(sorted));
        }
    }

    @Test
    public void testSortedDesc() {
        int[] a = new int[] {4, 3, 2, 1};
        for (Sorting sorter : sorters) {
            sorter.instanceSort(a);
            assertTrue(sorter.getClass().getSimpleName(), Sequence.isSorted(a));
        }
    }


    @Test
    public void testRandom() {
        int[] random = TestUtil.generateRandomIntArray(100, 0, 100);
        for (Sorting sorter : sorters) {
            sorter.instanceSort(random);
            assertTrue(sorter.getClass().getSimpleName(), Sequence.isSorted(random));
        }
    }
}
