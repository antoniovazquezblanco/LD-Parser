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
        return visitCommand_provide(ctx.command_provide());
    }

    private ProvideCommand visitCommand_provide(LDScriptParser.Command_provideContext ctx) {
        String symbolName = ctx.SYMBOL_NAME().getText();
        Expression expression = null;
        return new ProvideCommand(symbolName, expression);
    }
}
