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
        return null;
    }
}
