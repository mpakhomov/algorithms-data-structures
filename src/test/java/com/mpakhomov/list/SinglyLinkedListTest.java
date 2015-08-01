package com.mpakhomov.list;

import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;


/**
 * @author mpakhomov
 * @since 7/31/15
 */
public class SinglyLinkedListTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testAdd() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        List<Integer> actual = list.asList();
        assertThat(actual, contains(expected.toArray(new Integer[0])));
        assertThat(actual, contains(expected.toArray()));
    }

    @Test
    public void testToString() {
        SinglyLinkedList<Integer> emptyList = new SinglyLinkedList<>();
        assertThat(emptyList.toString(), equalTo("[]"));

        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertThat(list.toString(), equalTo("[1, 2, 3]"));
    }

    @Test
    public void testReverseIterative() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.reverseIterative();
        List<Integer> expected = Arrays.asList(3, 2, 1);
        List<Integer> actual = list.asList();
        assertThat(actual, contains(expected.toArray()));

        // test with empty list
        list = new SinglyLinkedList<>();
        list.reverseIterative();

        // test with list that consists of only one element
        list.add(1);
        list.reverseIterative();
        assertThat(list, contains(1));
    }

    @Test
    public void testReverseRecursive() {
        final SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        IntStream.rangeClosed(1, 10).forEach(list::add);
        list.reverseRecursive();
        Integer[] expected = new Integer[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        List<Integer> actual = list.asList();
        assertThat(actual, contains(expected));

        // test with empty list
        SinglyLinkedList list1 = new SinglyLinkedList<>();
        list1.reverseRecursive();

        // test with list that consists of only one element
        list1.add(1);
        list1.reverseRecursive();
        actual = list1.asList();
        assertThat(actual, contains(1));
    }



}
