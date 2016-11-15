package com.mpakhomov.algorithms.sort;

import com.mpakhomov.TestUtil;
import com.mpakhomov.seq.Sequence;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MergeSortTest {

    @Test
    public void testSort() {
        int[] a = TestUtil.generateRandomIntArray(100, 0, 100);
        MergeSort.sort(a);
        assertThat(Sequence.isSorted(a), is(true));
    }

    @Test
    public void testSortSimple() {
        int[] a = new int[] {3, 7, 2, 5};
        MergeSort.sort(a);
        assertThat(Sequence.isSorted(a), is(true));
    }
}
