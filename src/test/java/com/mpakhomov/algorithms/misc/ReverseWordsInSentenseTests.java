package com.mpakhomov.algorithms.misc;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class ReverseWordsInSentenseTests {

    @Test
    public void testReverse() {
        char[] src = "Today is Wednesday".toCharArray();
        char[] expected =  "Wednesday is Today".toCharArray();
        assertArrayEquals(ReverseWordsInSentense.reverse(src), expected);
    }

    @Test
    public void testReverse1() {
        char[] src = "Today is Wednesday".toCharArray();
        char[] expected =  "Wednesday is Today".toCharArray();
        assertArrayEquals(ReverseWordsInSentense.reverse1(src), expected);
    }
}
