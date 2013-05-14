package com.mpakhomov;

import java.util.Iterator;
import java.util.NoSuchElementException;
//import java.util.LinkedList

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 4/28/13
 * Time: 6:21 PM
 */
public class LinkedList<E> implements Iterable<E> {
    private Entry<E> head;

    public LinkedList() {
        head = null;
    }

    public LinkedList(E value) {  // Constructor for a list
        head = new Entry<E>(value, null);
    }

    public Boolean isEmpty() {
        return head == null;
    }


    public LinkedList<E> reverse() {
        LinkedList<E> tmp = new LinkedList<E>();
        for (Iterator<E> it = this.iterator(); it.hasNext();) {
            tmp.addFirst(it.next());
        }
        head = tmp.head;
        return this;
    }

    public LinkedList<E> addAll(LinkedList<E> other) {
        for(E element: other) addLast(element);
        return this;
    }

    public E removeFirst() {
        E val = getFirst();
        head = head.next;
        return val;
    }

    public LinkedList<E> concat(LinkedList<E> list1, LinkedList<E> list2) {
        LinkedList<E> newList = new LinkedList<E>();
        for (E el : list1) {
            list1.addLast(el);
        }
        for (E el : list1) {
            list2.addLast(el);
        }
        return newList;
    }


    public E getFirst() {
        return head.value;
    }

    public E getLast() {
        Entry<E> node = head;
        while(node.next != null) node = node.next;
        return node.value;
    }

    /**
     * Append a node to the end of the list
     **/
    public LinkedList<E> addLast(E value) {
        Entry<E> node = head;  // Start at this node

        // Loop 'till we find the last node in the list
        while(node.next != null) {
            node = node.next;
        }
        node.next = new Entry(value, null);
        return this;
    }

    public LinkedList<E> addFirst(E value) {
        head = new Entry<E>(value, head);
        return this;
    }

    private class LinkedListIterator implements Iterator<E> {
        private Entry<E> nextNode = head;

        LinkedListIterator() {
//            nextNode = head;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E val = nextNode.value;
            nextNode = nextNode.next;
            return val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Entry<E> curr = head; curr != null; curr = curr.next) {
            sb.append(curr.value);
            if (curr.next != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator iterator() {
        return new LinkedListIterator();
    }

    private static class Entry<E> {
        private E value;
        private Entry next;

        Entry(E value, Entry next) {
            this.value = value;
            this.next = next;
        }

    }
}
