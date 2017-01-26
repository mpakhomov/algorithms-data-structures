package com.mpakhomov.queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.hamcrest.Matchers.*;


import com.mpakhomov.queue.*;
import org.junit.Test;

public class CircularBufferTest {

    @Test
    public void testThatTheMostRecetnlyAddedElementIsDeleted() {
        CircularBuffer<Integer> buf = new CircularBuffer<Integer>(3);
        buf.offer(1);
        buf.offer(2);
        buf.offer(3);
        buf.offer(4);
        assertArrayEquals(buf.toArray(), new Object[] {2, 3, 4});
    }

    @Test
    public void testRandomAccess() {
        CircularBuffer<Integer> buf = new CircularBuffer<Integer>(3);
        buf.offer(1);
        buf.offer(2);
        buf.offer(3);
        buf.offer(4);
        assertThat(buf.get(0), is(2));
        assertThat(buf.get(1), is(3));
        assertThat(buf.get(2), is(4));
    }
}