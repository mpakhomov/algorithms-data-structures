package com.mpakhomov.algorithms;

import java.util.*;

public class BalancedParenthesis {

    static boolean check(String string) {
        Deque<Character> stack = new ArrayDeque<>();
        for (Character c : string.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')'){
                // check if c is closing an open parenthesis
                if (!stack.isEmpty() && stack.peekFirst() == '(') {
                    stack.pop();
                } else {
                    // if the closing parenthesis do not close anything previously opened, return false
                    return false;
                }
            }
        }
        // end of loop - check if all opened parenthesis were closed
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        stack.peek();
        stack.peekFirst();
        BalancedParenthesis.check(":=)");
    }

}