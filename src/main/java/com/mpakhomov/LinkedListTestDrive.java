package com.mpakhomov;

//import java.util.LinkedList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 4/28/13
 * Time: 6:44 PM
 */
public class LinkedListTestDrive {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.addFirst(1);
        list.addLast(2).addLast(3).addLast(4);
        list.reverse();

        for (Integer v: list) {
            System.out.println(v);
        }

        System.out.println(list.toString());
        list.addFirst(10).addFirst(11).addFirst(12);
        System.out.println(list.toString());
        list.addFirst(13);
        System.out.println(list.toString());
        System.out.println(list.reverse().toString());
        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        //System.out.println(list.reverseRecursively());
    }
}

