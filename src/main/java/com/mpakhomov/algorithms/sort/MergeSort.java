package com.mpakhomov.algorithms.sort;

/**
 * @author mpakhomov
 * @since 8/26/2015
 */
public class MergeSort {

    static public void sort(int[] a) {
        // create an auxiliary array to run merge step of the algorithm
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    static void sort(int[] a, int[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = (lo + hi) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        // copy to auxiliary array
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);

        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi ; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j] < aux[i]) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }
}
