package com.mpakhomov.algorithms.sort;

/**
 * Insertion sort iterates, consuming one input element each repetition, and growing a sorted output list.
 * Each iteration, insertion sort removes one element from the input data, finds the location it belongs within
 * the sorted list, and inserts it there. It repeats until no input elements remain
 * <p>
 * Despite the fact, that there are algorithms with better asymptotic complexity (heap sort, merge sort etc), insertion
 * sort is useful in some cases. For tiny arrays it beats more advanced algorithms. In fact, some JDK sorting
 * implementations use insertion sort under the covers: when array size is less than some {@link java.util.Arrays#INSERTIONSORT_THRESHOLD},
 * they don't recurse further and just sort the array using insertion sort.
 * <p>
 * Insertion sort is excellent for partially sorted arrays. Typical examples of partially sorted arrays are the
 * following (Robert Sedgewick, Kevin Wayne, Algorithms, Addison-Wesley, 2011):
 * <p><ul>
 * <li> An array where each entry is not far from its final position </li>
 * <li> A small array appended to a large sorted array </li>
 * <li> An array with only a few entries that are not in place </li>
 * </ul>
 * <p>
 * Performance: Average: O(n^2), Best: O(n), Worst: O(n^2)
 * Space: O(1) (in-place)
 * Stable: yes
 * Adaptive: yes
 *
 * @see <a href="https://en.wikipedia.org/wiki/Insertion_sort">https://en.wikipedia.org/wiki/Insertion_sort</a>
 * @see <a href="http://www.sorting-algorithms.com/insertion_sort">http://www.sorting-algorithms.com/insertion-sort</a>
 *
 * @author mpakhomov
 * @since 8/24/2015
 */
public class InsertionSort {

    /**
     * sort the array
     *
     * @param a array to be sorted
     */
    static public void sort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i - 1; j >= 0 && a[j] > a[j + 1]; j--) {
                exch(a, j, j + 1);
            }
        }
    }

    /**
     * optimized version of insertion sort. instead of swapping elements one by one, find the correct position for the
     * current element and call {@link System#arraycopy(Object, int, Object, int, int)}
     *
     * @param a array to be sorted
     */
    static public void sortWithArrayCopy(int[] a) {
        final int N = a.length;

        for (int i = 1; i < N; i++) {
            // find its proper position for the current element, shift elements a[properPos] ..  a[i-1]  one position
            // to the right and insert the current element to its proper location within the list
            int cur = a[i];
            int properPos = i;
            while (properPos - 1 >= 0 && cur < a[properPos - 1]) { properPos--;}
            if (properPos != i) {
                System.arraycopy(a, properPos, a, properPos + 1, i - properPos);
                a[properPos] = cur;
            }
        }
    }

    private static void exch(int a[], int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
