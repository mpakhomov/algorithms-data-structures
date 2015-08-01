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

        // Overridden, because I want to see a node pretty printed in debugger
        @Override
        public String toString() {
            return "(value: " + value + ", next: " + next + ")";
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

    /**
     * iterative version of list reversal
     */
    public void reverseIterative() {
        // return if list is empty or contains only one element
        if (head == null || head.next == null) {
            return;
        }

        Node<T> prev = null, curr = head, next = null;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        head = prev;
    }

    /**
     * recursive version of list reversal
     */
    public void reverseRecursive() {
        head = reverseRecursiveHelper(head);
    }

    // I took the solution from: http://stackoverflow.com/a/354937/1817777
    private Node<T> reverseRecursiveHelper(Node<T> list) {
        // base condition to terminate recursion:
        // either head is null or the list contain only one element
        if (list == null || list.next == null) {
            return list;
        }

        // third question - in Lisp this is easy, but we don't have cons
        // so we grab the second element (which will be the last after we reverse it)
        Node<T> secondElem = list.next;

        // bug fix - need to unlink list from the rest or you will get a cycle
        list.next = null;

        // then we reverse everything from the second element on
        Node<T> reverseRest = reverseRecursiveHelper(secondElem);

        // then we join the two lists
        secondElem.next = list;

        return reverseRest;

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
        // it's legal because we implement Iterable<T>
        for (T e : this) {
            list.add(e);
        }
        return list;
    }

    /**
     * Return string in the following format: [e1, e2, ... , en]
     *
     * @return string representation of the list
     */
    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (T e : this) {
            sb.append(e).append(", ");
        }
        int length = sb.length();
        // remove last comma and apace ", "
        if (length > 2) {
            sb.setLength(length - 2);
        }
        sb.append("]");
        return sb.toString();
    }
}
