package com.mpakhomov.algorithms.sort;

/**
 * @author mpakhomov
 * @since 8/24/2015
 */
public class SelectionSort {

    /**
     * sort the array into ascending order. runs in O(n^2), memory: O(1)
     *
     * @param a array to be sorted
     */
    public static void sort(int a[]) {
        final int N = a.length;
        for (int i = 0; i < N; i++) {
            // find the minimum element and put it into position i
            int indexOfMin = i;
            for (int j = i + 1; j < N; j++) {
                if (a[j] < a[indexOfMin]) {
                    indexOfMin = j;
                }
            }
            if (i != indexOfMin){
                exch(a, i, indexOfMin);
            }
        }
    }

    private static void exch(int a[], int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
