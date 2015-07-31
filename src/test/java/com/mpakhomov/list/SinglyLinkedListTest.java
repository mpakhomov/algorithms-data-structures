package com.mpakhomov.list;

import org.junit.*;

import java.util.*;
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

}
