package com.mpakhomov;

/**
 * Created by mike on 6/24/14.
 */
public class SinglyLinkedList<E> {

    public E value;
    public SinglyLinkedList<E> next;

    public SinglyLinkedList(E value) {
        this.value = value;
        this.next = null;
    }


    public static void main(String[] args) {
        SinglyLinkedList<Integer> root = new SinglyLinkedList<Integer>(1);
        root.next = new SinglyLinkedList<Integer>(2);
        root.next.next = new SinglyLinkedList<Integer>(3);
        root.next.next.next = new SinglyLinkedList<Integer>(4);
        printList(root);
        SinglyLinkedList<Integer> list = iterativeReverse(root);
        printList(list);
        list = recursiveReverse(list);
        printList(list);
        System.out.println("---------");
        list = recursiveReverse1(list);
        printList(list);
    }

    public static SinglyLinkedList iterativeReverse(SinglyLinkedList list) {

        if (list == null || list.next == null) {
            return list;
        }

        SinglyLinkedList prev, curr, next;
        prev = null;
        next = null;
        curr = list;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }

    public static SinglyLinkedList recursiveReverse(SinglyLinkedList list) {

        // check for empty or size 1 linked list. This is a base condition to
        // terminate recursion.
        if (list == null || list.next == null) {
            return list;
        }

        SinglyLinkedList remainingReverse = recursiveReverse(list.next);

        // update the tail as beginning
        SinglyLinkedList current = remainingReverse;
        while (current.next != null) {
            current = current.next;

        }
        // assign the head as a tail
        current.next = list;
        list.next = null;

        return remainingReverse;
    }

    public static SinglyLinkedList recursiveReverse1(SinglyLinkedList list) {
        if (list == null) return null; // first question

        if (list.next == null) return list; // second question

        // third question - in Lisp this is easy, but we don't have cons
        // so we grab the second element (which will be the last after we reverse it)
        SinglyLinkedList secondElem = list.next;

        // bug fix - need to unlink list from the rest or you will get a cycle
        list.next = null;

        // then we reverse everything from the second element on
        SinglyLinkedList reverseRest = recursiveReverse1(secondElem);

        // then we join the two lists
        secondElem.next = list;

        return reverseRest;

    }

    public static void printList(SinglyLinkedList list) {
        while (list != null) {
            System.out.println(list.value);
            list = list.next;
        }
    }


}
