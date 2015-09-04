package com.mpakhomov.algorithms;

import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class BalancedParenthesisTest {

    private String goodStr = "(if (zero? x) max (/ 1 x))";
    private String goodStr1 = "I told him (that it’s not (yet) done). (But he wasn’t listening)";

    private String badStr = ":-)";
    private String badStr1 = "())(";
    private String badStr2 = "((())(";

    @Test
    public void testGood() {
        assertThat(BalancedParenthesis.check(goodStr), is(true));
        assertThat(BalancedParenthesis.check(goodStr1), is(true));
    }

    @Test
    public void testBad() {
        assertThat(BalancedParenthesis.check(badStr), is(false));
        assertThat(BalancedParenthesis.check(badStr1), is(false));
        assertThat(BalancedParenthesis.check(badStr2), is(false));
    }


}