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
public class SelectionSortTest {

    @Test
    public void testSort() {
        int[] a = TestUtil.generateRandomIntArray(100, 0, 100);
        SelectionSort.sort(a);
        assertThat(Sequence.isSorted(a), is(true));
    }
}
