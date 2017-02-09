package com.mpakhomov.algorithms.misc;
import org.junit.*;

import static org.junit.Assert.*;


public class ReverseWordsInSentenceTests {

    @Test
    public void testReverseWords() {
        char[] src = "Today is Wednesday".toCharArray();
        char[] expected =  "Wednesday is Today".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testLeadingSpaces() {
        char[] src = "  Today is Wednesday".toCharArray();
        char[] expected =  "Wednesday is Today  ".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testTrailingSpaces() {
        char[] src = "Today is Wednesday  ".toCharArray();
        char[] expected =  "  Wednesday is Today".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testMoreThan1Space() {
        char[] src = " Today  is  Wednesday ".toCharArray();
        char[] expected =  " Wednesday  is  Today ".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testMoreThan1SpaceAndOneLetterWord() {
        char[] src = " Today  is  Wednesday 1 ".toCharArray();
        char[] expected =  " 1 Wednesday  is  Today ".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testMoreThan1SpaceAndOneLetterWordAtTheEnd() {
        char[] src = " Today  is  Wednesday 1".toCharArray();
        char[] expected =  "1 Wednesday  is  Today ".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testOneWordOnly() {
        char[] src = " Today ".toCharArray();
        char[] expected =  " Today ".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testEmptyString() {
        char[] src = "".toCharArray();
        char[] expected =  "".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }

    @Test
    public void testEmptySentenceWithASpace() {
        char[] src = " ".toCharArray();
        char[] expected =  " ".toCharArray();
        assertArrayEquals(ReverseWordsInSentence.reverseWords(src), expected);
    }
}
