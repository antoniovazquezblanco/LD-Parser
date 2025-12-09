package io.ldparser;

/**
 * Base class for expressions in LD script.
 */
public class Expression {
    private final String expressionString;
    private final long numericalValue;

    public Expression(String expressionString, long numericalValue) {
        this.expressionString = expressionString;
        this.numericalValue = numericalValue;
    }

    /**
     * Returns the string representation of the full expression.
     */
    public String getExpressionString() {
        return expressionString;
    }

    /**
     * Returns the numerical value of the expression.
     */
    public long getNumericalValue() {
        return numericalValue;
    }
}
