package com.mpakhomov.list;

import java.util.*;

/**
 * It's a generic implementation of singly linked list. Each node has only one reference to the next node
 *
 * @author mpakhomov
 * @since 7/31/15
 */
public class SinglyLinkedList<T> implements Iterable<T> {

    private Node<T> head = null;

    static class Node<T> {
        private T value;
        private Node<T> next = null;

        public Node(T value) {
            this.value = value;
        }
    }

    /**
     * add element to the list
     *
     * @param value element to be added
     */
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            return;
        }

        Node<T> cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }

        cur.next = newNode;
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> cur = head;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T val = cur.value;
            cur = cur.next;
            return val;
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    /**
     * utility method, that builds up a List from the collection. It's useful in unit testing
     * <pre>{@code
     *   SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
     *   list.add(1);
     *   list.add(2);
     *   list.add(3);
     *   List<Integer> expected = Arrays.asList(1, 2, 3);
     *   List<Integer> actual = list.asList();
     *   assertThat(actual, contains(expected.toArray(new Integer[0])));
     * }
     * </pre>
     *
     * @return list of elements
     */
    public List<T> asList() {
        ArrayList<T> list = new ArrayList<>();
        Iterator<T> iterator = iterator();
        // it's legal because we implement Iterable<T>
        for (T e : this) {
            list.add(e);
        }
        return list;
    }
}
