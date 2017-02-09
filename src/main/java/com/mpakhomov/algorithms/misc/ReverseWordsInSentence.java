package com.mpakhomov.algorithms.misc;

/*
     reverse words in sentence not using any library functions
     Today is Wednesday => Wednesday is Today
 */
public class ReverseWordsInSentence {

    public static char[] reverseWords(char[] src) {
        char[] dest = new char[src.length];
        for (int i = src.length - 1; i >= 0; i--) dest[src.length - 1 - i] = src[i];

        int start = -1; // index of the beginning of the word
        int end = -1; // index of the end of the word
        for (int i = 0; i <= dest.length - 1; i++) {
            if (dest[i] != ' ' && start == -1) {
                // mark the start of the word
                start = i;
                continue;
            } else if (dest[i] == ' ' && start != -1 && end == - 1) {
                // mark the end of the word
                end = i - 1;
            } else if (i == dest.length - 1 && start != -1) {
                // the end of the sentence, we should reverse the last word
                end = i;
            } else {
                continue;
            }
            reverseInPlace(dest, start, end);
            start = -1;
            end = -1;
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
