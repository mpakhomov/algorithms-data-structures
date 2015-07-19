package com.mpakhomov.seq;

import org.junit.*;

import java.util.*;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 * @author: mpakhomov
 */
public class SeqTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testJoinIteratorsBasic() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);
        List<Integer> list3 = Arrays.asList(7, 8, 9);
        Iterator<Integer> it = Sequence.joinIterators(list1.iterator(), list2.iterator(), list3.iterator());
        for (int i = 1; i <= 9; i++) {
            assertThat(it.hasNext(), is(true));
            assertThat(it.next(), equalTo(i));
        }
    }

    /**
     * test that {@link Iterator#hasNext()} doesn't change iterator's value
     */
    @Test
    public void testJoinIteratorsHasNext() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);
        List<Integer> list3 = Arrays.asList(7, 8, 9);
        Iterator<Integer> it = Sequence.joinIterators(list1.iterator(), list2.iterator(), list3.iterator());
        for (int i = 1; i <= 9; i++) {
            assertThat(it.hasNext(), is(true));
        }

        assertThat(it.next(), equalTo(1));

        for (int i = 2; i <= 9; i++) {
            assertThat(it.next(), equalTo(i));
        }

        // now it should point to null
        assertThat(it.next(), is(nullValue()));
    }
}
