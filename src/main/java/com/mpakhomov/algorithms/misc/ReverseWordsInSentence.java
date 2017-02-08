package com.mpakhomov.algorithms.misc;

/*
     reverse words in sentence not using any library functions
     Today is Wednesday => Wednesday is Today
 */
public class ReverseWordsInSentence {

    public static char[] reverse(char[] src) {
        int wordCount = 0;
        for (int i = 0; i < src.length; i++) if (src[i] == ' ') wordCount++;
        wordCount++;

        // indexes of the beginning of each word
        int[] words = new int[wordCount];
        int j = 1;
        for (int i = 1; i < src.length; i++) if (src[i - 1] == ' ') words[j++] = i;

        char[] dest = new char[src.length];
        int destCount = 0;
        for (int i = words.length - 1; i >= 0; i--) {
            char[] word = slice(src, words[i], i == words.length - 1 ? src.length - 1 : words [i + 1] - 2);
            for (char c : word) dest[destCount++] = c;
            if (i != 0) dest[destCount++] = ' ';
        }
        return dest;
    }

    /**
     * return a subarray of a given array
     * @param src input array
     * @param begin begin index
     * @param end index (inclusive)
     * @return subarray
     */
    public static char[] slice(char[] src, int begin, int end) {
        char[] dest = new char[end - begin + 1];
        int j = 0;
        for (int i = begin; i <= end; i++) dest[j++] = src[i];
        return dest;
    }

    public static char[] reverse1(char[] src) {
        char[] dest = new char[src.length];
        for (int i = src.length - 1; i >= 0; i--) dest[src.length - 1 - i] = src[i];

        int prev = 0;
        for (int i = 0; i <= dest.length; i++) {
            if (i == dest.length || dest[i] == ' ') {
                reverseInPlace(dest, prev, i - 1);
                prev = i + 1;
            }
        }

        return dest;
    }

    public static void reverseInPlace(char[] src, int lo, int hi) {
        int mid = (hi + lo) / 2;
        for (int i = lo; i <= mid; i++) swap(src, i, hi - (i - lo));
    }

    public static void swap(char[] src, int i, int j) {
        char tmp = src[i];
        src[i] = src[j];
        src[j] = tmp;
    }
}
