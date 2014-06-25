package com.mpakhomov;




/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 11.07.13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public class ReverseLinkedList {

    static class Node<E>
    {
        Node<E> next;
        E value;

        public Node(E value, Node<E> next)
        {
            this.value = value;
            this.next = next;
        }

        public Node<E> reverse(Node<E> root)
        {
            Node<E> head = null;
            Node<E> current = root;
            while (current != null) {
                Node<E> save = current;
                current = current.next;
                save.next = head;
                head = save;
            }
            return head;
        }
    }

    public static void main(String[] args) {
        Node<Integer> first = new Node<Integer>(3, null);
        Node<Integer> second = new Node<Integer>(2, null);
        first.next = second;
        Node<Integer> third = new Node<Integer>(1, null);
        second.next = third;
        printList(first);
        first.reverse(first);
        printList(first);

    }

    public static void printList(Node<Integer> root) {
        Node<Integer> current = root;
        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

}
