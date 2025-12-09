package io.ldparser;

/**
 * Represents a PROVIDE command in LD script.
 */
public class ProvideCommand extends Command {
    private final String symbolName;
    private final Expression expression;

    public ProvideCommand(String symbolName, Expression expression) {
        this.symbolName = symbolName;
        this.expression = expression;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public Expression getExpression() {
        return expression;
    }
}