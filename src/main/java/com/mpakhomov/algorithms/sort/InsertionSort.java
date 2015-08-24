package com.mpakhomov.algorithms.sort;

/**
 * @author mpakhomov
 * @since 8/24/2015
 */
public class InsertionSort {

    /**
     * Sort the array. Insertion Sort is an elementary quadratic algorithm, but it's useful in some cases:
     *  <p><ul>it's good for small arrays
     *  <ul>for partially sorted arrays its running time is proportional to N + number of inversions
     *  <ul>other effective algorithms may use insertion sort for the base case, i.e. when number of elements is less
     *      than some threshold, they stop the recursion and sort the array using insertion sort
     * <p> time: 0(N^2), best case: O(n), memory: O(1)
     *
     * @param a array to be sorted
     */
    static public void sort(int[] a) {
        final int N = a.length;

        for (int i = 1; i < N; i++) {
            // find the right position for the current element, shift [ a[insertionPos],  a[i-1] ] one position
            // to the right and insert cur to the insertPos
            int cur = a[i];
            int insertionPos = i;
            while (insertionPos - 1 >= 0 && cur < a[insertionPos - 1]) { insertionPos--;}
            if (insertionPos != i) {
                System.arraycopy(a, insertionPos, a, insertionPos + 1, i - insertionPos);
                a[insertionPos] = cur;
            }
        }
    }

    private static void exch(int a[], int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
