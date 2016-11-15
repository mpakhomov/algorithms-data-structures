package com.mpakhomov.algorithms.sort;

import com.mpakhomov.TestUtil;
import com.mpakhomov.seq.Sequence;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author mpakhomov
 * @since 8/24/2015
 */
public class InsertionSortTest {

    @Test
    public void testSortWithArrayCopy() {
        int[] a = new int[] {3, 7, 2, 5};
        InsertionSort.sortWithArrayCopy(a);
        assertThat(Sequence.isSorted(a), is(true));

        int[] random = TestUtil.generateRandomIntArray(100, 0, 100);
        InsertionSort.sortWithArrayCopy(random);
        assertThat(Sequence.isSorted(random), is(true));

        int[] sorted = new int[] {1, 2, 3, 4};
        InsertionSort.sortWithArrayCopy(sorted);
        assertThat(Sequence.isSorted(sorted), is(true));
    }
}
