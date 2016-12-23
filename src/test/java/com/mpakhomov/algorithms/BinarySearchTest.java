package com.mpakhomov.algorithms;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by mike on 11/12/16.
 */
public class BinarySearchTest {

    @Test
    public void simpleTest() {
        int[] a = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
        int index = BinarySearch.search(a, 2);
        assertThat(index, is(equalTo(1)));
    }

    @Test
    public void oneElementArrayFound() {
        int[] a = new int[] {1};
        int index = BinarySearch.search(a, 1);
        assertThat(index, is(equalTo(0)));
    }

    @Test
    public void oneElementArrayNotFound() {
        int[] a = new int[] {1};
        int index = BinarySearch.search(a, 2);
        assertThat(index, is(equalTo(-1)));
    }

    @Test
    public void twoElementsArrayFind1() {
        int[] a = new int[] {1, 2};
        int index = BinarySearch.search(a, 1);
        assertThat(index, is(equalTo(0)));
    }

    @Test
    public void twoElementsArrayFind2() {
        int[] a = new int[] {1, 2};
        int index = BinarySearch.search(a, 2);
        assertThat(index, is(equalTo(1)));
    }

    @Test
    public void twoElementsArrayNotFound() {
        int[] a = new int[] {1, 2};
        int index = BinarySearch.search(a, 3);
        assertThat(index, is(equalTo(-1)));
    }

    @Test
    public void threeElementsArray() {
        int[] a = new int[] {1, 2, 3};
        int index = BinarySearch.search(a, 1);
        assertThat(index, is(equalTo(0)));

        index = BinarySearch.search(a, 2);
        assertThat(index, is(equalTo(1)));

        index = BinarySearch.search(a, 3);
        assertThat(index, is(equalTo(2)));

        index = BinarySearch.search(a, 4);
        assertThat(index, is(equalTo(-1)));
    }

    @Test
    public void arrayAscendingOrder() {
        final int max = 128;
        int[] a = new int[max];
        for (int i = 0; i < max; i++) a[i] = i;

        for (int i = 0; i < max; i++) {
            int index = BinarySearch.search(a, i);
            assertThat(index, is(equalTo(i)));
        }

        assertThat(BinarySearch.search(a, max + 1), is(-1));
    }

    @Test
    public void emptyArray() {
        int[] a = new int[]{};
        int index = BinarySearch.search(a, 42);
        assertThat(index, is(-1));
    }

}
