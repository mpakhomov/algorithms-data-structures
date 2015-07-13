package com.mpakhomov.seq;

import java.util.Iterator;
import java.util.List;

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
    public static <T extends Comparable<T>> boolean isSorted(T[] array) {
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
     *
     * @param it iterator
     * @param <T> type that implements {@link Comparable}
     * @return true if the sequence is sorted, false otherwise
     */
    public static <T extends Comparable<T>> boolean isSorted(Iterator<T> it) {
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
    public static <T extends Comparable<T>> boolean isSorted(List<T> list) {
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
}
