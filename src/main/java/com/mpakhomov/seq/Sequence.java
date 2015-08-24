package com.mpakhomov.seq;

import java.util.*;

/**
 * @author mpakhomov
 * @since: 7/13/2015
 */
public class Sequence {

    /**
     *
     * @param array array
     * @param <T> type that implements {@link Comparable}
     * @return true if array is sorted, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isSorted(T[] array) {
        if (array.length < 2) {
            return true;
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1].compareTo(array[i]) > 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * checks if the array is sorted
     *
     * @param array the array
     * @return true if sorted, false otherwise
     */
    public static boolean isSorted(int[] array) {
        if (array.length < 2) {
            return true;
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param it iterator
     * @param <T> type that implements {@link Comparable}
     * @return true if the sequence is sorted, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isSorted(Iterator<T> it) {
        T prev  = null;
        while (it.hasNext()) {
            T cur = it.next();
            if (prev != null && prev.compareTo(cur) > 0) {
                return false;
            }
            prev = cur;
        }
        return true;
    }

    /**
     *
     * @param list list
     * @param <T> type that implements {@link Comparable}
     * @return true if the sequence is sorted, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isSorted(List<T> list) {
        T prev = null;
        for (T e : list) {
            T cur = e;
            if (prev != null && prev.compareTo(cur) > 0) {
                return false;
            }
            prev = cur;
        }
        return true;
    }

    /**
     * Combines multiple iterators into a single iterator. The returned iterator
     * iterates across the elements of each iterator in {@code iterators}. The input
     * iterators are not polled until necessary.
     *
     * @param iterators input iterators
     * @param <T>
     * @return iterator that iterates across across all elements of input iterators
     */
    public static <T> Iterator<T> joinIterators(Iterator<T>... iterators)  {
        final Deque<T> queue = new ArrayDeque<>((Collection<T>) Arrays.asList(iterators));

        return new Iterator<T>() {

            private Iterator<T> iterator = (Iterator<T>) queue.poll();

            @Override
            public boolean hasNext() {
                if (iterator == null && queue.isEmpty()) {
                    return false;
                } else if (iterator == null) {
                    iterator = (Iterator<T>) queue.poll();
                }

                if (!iterator.hasNext()) {
                    iterator = (Iterator<T>) queue.poll();
                }

                if (iterator != null) {
                    return iterator.hasNext();
                }
                return false;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    return iterator.next();
                } else {
                    return null;
                }
            }
        };
    }
}
