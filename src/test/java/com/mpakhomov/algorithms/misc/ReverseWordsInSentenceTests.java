package com.mpakhomov.algorithms.misc;
import org.junit.*;

import static org.junit.Assert.*;


public class ReverseWordsInSentenceTests {

    @Test
    public void testReverse() {
        char[] src = "Today is Wednesday".toCharArray();
        char[] expected =  "Wednesday is Today".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testReverse1() {
        char[] src = "Today is Wednesday".toCharArray();
        char[] expected =  "Wednesday is Today".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords1(src), expected);
    }
}
