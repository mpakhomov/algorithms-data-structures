package com.mpakhomov.algorithms.sort;

import com.mpakhomov.TestUtil;
import com.mpakhomov.seq.Sequence;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author mpakhomov
 * @since 8/26/2015
 */
public class BubbleSortTest {

    @Test
    public void testBubbleSort() {
        int[] a = new int[]{3, 7, 2, 5};
        BubbleSort.sort(a);
        assertThat(Sequence.isSorted(a), is(true));

        int[] random = TestUtil.generateRandomIntArray(100, 0, 100);
        BubbleSort.sort(random);
        assertThat(Sequence.isSorted(random), is(true));

        int[] sorted = new int[]{1, 3, 5, 7};
        BubbleSort.sort(a);
        assertThat(Sequence.isSorted(sorted), is(true));
    }

    @Test
    public void testBubbleSortNaive() {
        int[] a = new int[]{3, 7, 2, 5};
        BubbleSort.sortNaive(a);
        assertThat(Sequence.isSorted(a), is(true));

        int[] random = TestUtil.generateRandomIntArray(100, 0, 100);
        BubbleSort.sortNaive(random);
        assertThat(Sequence.isSorted(random), is(true));
    }
}
