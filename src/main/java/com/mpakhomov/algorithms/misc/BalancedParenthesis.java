package com.mpakhomov.algorithms.misc;

import com.google.common.collect.*;
import com.google.common.primitives.*;

import java.util.*;

/**
 * This class verifies the balancing of parentheses in a string. It contains both iterative and recursive
 * algorithms.
 * <p>
 * Examples:
 * <p>
 * <code>"(if (zero? x) max (/ 1 x))"<code/> is a valid string
 * <p>
 * <code>":-)"<code/> is not a valid string
 * <p>
 * This is an exercise from <a href="https://www.coursera.org/course/progfun">Functional Programming Principles in Scala</a>
 * <p>
 * Credits:
 * <p>
 * <a href="http://stackoverflow.com/a/12541361/1817777">http://stackoverflow.com/a/12541361/1817777</a>
 * <p>
 * <a href=" http://stackoverflow.com/questions/2711032/basic-recursion-check-balanced-parenthesis"> http://stackoverflow.com/questions/2711032/basic-recursion-check-balanced-parenthesis</a>
 */
public class BalancedParenthesis {

    /**
     * @param string which contains parenthesis
     * @return true if valid, false otherwise
     */
    public static boolean check(String string) {
        Deque<Character> stack = new ArrayDeque<>();
        for (Character c : string.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    // if the closing parenthesis do not close anything previously opened, return false
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        // end of the loop - check if all opened parenthesis were closed
        return stack.isEmpty();
    }


    /**
     * recursive version with mutable state
     *
     * @param string which contains parenthesis
     * @return true if valid, false otherwise
     */
    public static boolean checkRecursive(String string) {
        Queue<Character> remaining = new ArrayDeque<>(Chars.asList(string.toCharArray()));
        return checkRecursiveHelper(remaining, false);
    }

    // the drawback of this solution is that we mutate the remaining queue, which is not pure functional
    private static boolean checkRecursiveHelper(Queue<Character> remaining, boolean needClosingParenthesis) {
        Character c;
        while ((c = remaining.poll()) != null) {
            if (c == '(') {
                // if the recursive call is unsuccessful - end the check, the answer is no
                if (!checkRecursiveHelper(remaining, true))
                    return false;
            } else if (c == ')') {
                if (needClosingParenthesis) {
                    // found matching parenthesis
                    return true;
                } else {
                    // if the closing parenthesis do not close anything previously opened, return false
                    return false;
                }
            }
        }
        // end of the loop - check if all opened parenthesis were closed
        return needClosingParenthesis == false;

    }

//    FUNCTION isBalanced(String input, String stack) : boolean
//      IF isEmpty(input)
//          RETURN isEmpty(stack)
//      ELSE IF isOpen(firstChar(input))
//          RETURN isBalanced(allButFirst(input), stack + firstChar(input))
//      ELSE IF isClose(firstChar(input))
//          RETURN NOT isEmpty(stack) AND isOpen(lastChar(stack))
//          AND isBalanced(allButFirst(input), allButLast(stack))
//      ELSE
//          RETURN isBalanced(allButFirst(input),stack)
    /**
     * pure functional implementation, add to the front of the stack
     *
     * @param string which contains parenthesis
     * @return true if valid, false otherwise
     */
    public static boolean checkRecursiveFunctional(String string) {
        final List<Character> remaining = Chars.asList(string.toCharArray());
        return checkRecursiveFunctionalHelper(remaining, new ArrayList<>());
    }

    // here, for convenience, I add to the front of the stack
    private static boolean checkRecursiveFunctionalHelper(final List<Character> remaining, final List<Character> stack) {
        return
            remaining.isEmpty() ?
                stack.isEmpty()
            : remaining.get(0) == '(' ? // if open parenthesis
                checkRecursiveFunctionalHelper(
                        remaining.subList(1, remaining.size()),
                        Lists.asList('(', stack.toArray(new Character[] {})) // same as x::xs
                )
            : remaining.get(0) == ')' ? // if close parenthesis
                !stack.isEmpty() /*&& stack.get(0) == '('*/ &&
                        checkRecursiveFunctionalHelper(
                            remaining.subList(1, remaining.size()),
                            stack.subList(1, stack.size())
                        )
            // skip non-parenthesis char
            : checkRecursiveFunctionalHelper(remaining.subList(1, remaining.size()), stack);
    }

    /**
     * pure functional implementation, add to the tail of the stack
     *
     * @param string which contains parenthesis
     * @return true if valid, false otherwise
     */
    public static boolean checkRecursiveFunctional1(String string) {
        final List<Character> remaining = Chars.asList(string.toCharArray());
        return checkRecursiveFunctionalHelper1(remaining, new ArrayList<>());
    }

    // here, I add to the tail of the stack
    private static boolean checkRecursiveFunctionalHelper1(final List<Character> remaining, final List<Character> stack) {
        return
            remaining.isEmpty() ?
                    stack.isEmpty()
                    : remaining.get(0) == '(' ? // if open parenthesis
                        checkRecursiveFunctionalHelper1(
                                remaining.subList(1, remaining.size()),
                                ImmutableList.<Character>builder().addAll(stack).add('(').build() // x::xs::'('
                        )
                    : remaining.get(0) == ')' ? // if close parenthesis
                        !stack.isEmpty() /*&& stack.get(stack.size() - 1) == '('*/ &&
                                checkRecursiveFunctionalHelper1(
                                        remaining.subList(1, remaining.size()),
                                        stack.subList(0, stack.size() - 1)
                                )
                    // skip non-parenthesis char
                    : checkRecursiveFunctionalHelper1(remaining.subList(1, remaining.size()), stack);
    }
}