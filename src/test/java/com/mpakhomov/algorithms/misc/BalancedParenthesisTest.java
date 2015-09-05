package com.mpakhomov.algorithms.misc;

import com.mpakhomov.algorithms.misc.*;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class BalancedParenthesisTest {

    private String goodStr = "(if (zero? x) max (/ 1 x))";
    private String goodStr1 = "I told him (that it’s not (yet) done). (But he wasn’t listening)";
    private String goodStr2 = "assertThat(BalancedParenthesis.check(goodStr), is(true));";

    private String badStr = ":-)";
    private String badStr1 = "())(";
    private String badStr2 = "((())(";

    @Test
    public void testGood() {
        assertThat(BalancedParenthesis.check(goodStr), is(true));
        assertThat(BalancedParenthesis.check(goodStr1), is(true));
        assertThat(BalancedParenthesis.check(goodStr2), is(true));

        assertThat(BalancedParenthesis.checkRecursive(goodStr), is(true));
        assertThat(BalancedParenthesis.checkRecursive(goodStr1), is(true));
        assertThat(BalancedParenthesis.checkRecursive(goodStr2), is(true));

        assertThat(BalancedParenthesis.checkRecursiveFunctional(goodStr), is(true));
        assertThat(BalancedParenthesis.checkRecursiveFunctional(goodStr1), is(true));
        assertThat(BalancedParenthesis.checkRecursiveFunctional(goodStr2), is(true));

        assertThat(BalancedParenthesis.checkRecursiveFunctional1(goodStr), is(true));
        assertThat(BalancedParenthesis.checkRecursiveFunctional1(goodStr1), is(true));
        assertThat(BalancedParenthesis.checkRecursiveFunctional1(goodStr2), is(true));
    }

    @Test
    public void testBad() {
        assertThat(BalancedParenthesis.check(badStr), is(false));
        assertThat(BalancedParenthesis.check(badStr1), is(false));
        assertThat(BalancedParenthesis.check(badStr2), is(false));

        assertThat(BalancedParenthesis.checkRecursive(badStr), is(false));
        assertThat(BalancedParenthesis.checkRecursive(badStr1), is(false));
        assertThat(BalancedParenthesis.checkRecursive(badStr2), is(false));

        assertThat(BalancedParenthesis.checkRecursiveFunctional(badStr), is(false));
        assertThat(BalancedParenthesis.checkRecursiveFunctional(badStr1), is(false));
        assertThat(BalancedParenthesis.checkRecursiveFunctional(badStr2), is(false));

        assertThat(BalancedParenthesis.checkRecursiveFunctional1(badStr), is(false));
        assertThat(BalancedParenthesis.checkRecursiveFunctional1(badStr1), is(false));
        assertThat(BalancedParenthesis.checkRecursiveFunctional1(badStr2), is(false));

    }

}