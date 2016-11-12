package com.mpakhomov.algorithms;

/**
 * Created by mike on 11/12/16.
 */
public class BinarySearch {
    /**
     * binary search algorithm. running time O(log N)
     * @param a sorted array
     * @param key
     * @return an index of the found element, when the array contains given key. otherwise returns -1
     */
    public static int search(int[] a, int key) {
        if (a.length == 0) return -1;
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            }  else if (key > a[mid]) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
