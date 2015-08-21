package com.mpakhomov.list;

import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
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

    @Test
    public void testEqualsAndHashCode() {
        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<>();
        IntStream.rangeClosed(1, 3).forEach(list1::add);

        SinglyLinkedList<Integer> list2 = new SinglyLinkedList<>();
        IntStream.rangeClosed(1, 3).forEach(list2::add);

        SinglyLinkedList<Object> list3 = new SinglyLinkedList<>();
        IntStream.rangeClosed(1, 3).forEach(i -> list3.add(Integer.valueOf(i)));

        assertThat(list1, equalTo(list2));
        assertThat(list2, equalTo(list3));
        assertThat(list1.hashCode(), equalTo(list2.hashCode()));
        assertThat(list2.hashCode(), equalTo(list3.hashCode()));

        SinglyLinkedList<Integer> list4 = new SinglyLinkedList<>();
        IntStream.rangeClosed(1, 4).forEach(list4::add);

        assertThat(list1, not(equalTo(list4)));
        assertThat(list1.hashCode(), not(equalTo(list4.hashCode())));
    }



}
