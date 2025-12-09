package io.ldparser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor that builds the Script model from the parse tree.
 */
public class ScriptBuilderVisitor {

    public Script visit(ParseTree tree) {
        if (tree instanceof LDScriptParser.ScriptContext) {
            return visitScript((LDScriptParser.ScriptContext) tree);
        }
        throw new IllegalArgumentException("Expected ScriptContext");
    }

    private Script visitScript(LDScriptParser.ScriptContext ctx) {
        List<Command> commands = new ArrayList<>();
        for (LDScriptParser.CommandContext cmdCtx : ctx.command()) {
            commands.add(visitCommand(cmdCtx));
        }
        return new Script(commands);
    }
    
    private Command visitCommand(LDScriptParser.CommandContext ctx) {
        return visitCommandProvide(ctx.commandProvide());
    }

    private ProvideCommand visitCommandProvide(LDScriptParser.CommandProvideContext ctx) {
        String symbolName = visitSymbolName(ctx.symbolName());
        Expression expression = visitExpression(ctx.expression());
        return new ProvideCommand(symbolName, expression);
    }

    private String visitSymbolName(LDScriptParser.SymbolNameContext ctx) {
        if (ctx.SYMBOL_NAME_UNQUOTED() != null) {
            return ctx.SYMBOL_NAME_UNQUOTED().getText();
        } else if (ctx.SYMBOL_NAME_QUOTED() != null) {
            String text = ctx.SYMBOL_NAME_QUOTED().getText();
            // Remove quotes
            return text.substring(1, text.length() - 1);
        }
        throw new IllegalArgumentException("Unknown symbol name type");
    }

    private Expression visitExpression(LDScriptParser.ExpressionContext ctx) {
        LDScriptParser.ConstantContext constantCtx = ctx.constant();
        String expressionString = constantCtx.getText();
        long numericalValue = visitConstant(constantCtx);
        return new Expression(expressionString, numericalValue);
    }

    private long visitConstant(LDScriptParser.ConstantContext ctx) {
        if (ctx.CONSTANT_OCT() != null) {
            String text = ctx.CONSTANT_OCT().getText();
            return Long.parseLong(text, 8);
        } else if (ctx.CONSTANT_DEC() != null) {
            String text = ctx.CONSTANT_DEC().getText();
            // Handle optional K/M suffix
            char lastChar = text.charAt(text.length() - 1);
            long multiplier = 1;
            if (lastChar == 'K' || lastChar == 'M') {
                text = text.substring(0, text.length() - 1);
                multiplier = (lastChar == 'K') ? 1024 : 1024 * 1024;
            }
            return Long.parseLong(text) * multiplier;
        } else if (ctx.CONSTANT_HEX() != null) {
            String text = ctx.CONSTANT_HEX().getText();
            return Long.parseLong(text.substring(2), 16);
        }
        throw new IllegalArgumentException("Unknown constant type");
    }
}
