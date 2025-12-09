package io.ldparser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Main API class for parsing LD scripts.
 */
public class LDParser {

    /**
     * Parses an LD script from a file.
     *
     * @param file the file to parse
     * @return the parsed script
     * @throws IOException if an I/O error occurs
     */
    public static Script parse(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return parse(fis);
        }
    }

    /**
     * Parses an LD script from an input stream.
     *
     * @param inputStream the input stream to parse
     * @return the parsed script
     * @throws IOException if an I/O error occurs
     */
    public static Script parse(InputStream inputStream) throws IOException {
        CharStream charStream = CharStreams.fromStream(inputStream);
        LDScriptLexer lexer = new LDScriptLexer(charStream);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LDScriptParser parser = new LDScriptParser(tokens);

        ParseTree tree = parser.script();

        ScriptBuilderVisitor visitor = new ScriptBuilderVisitor();
        return visitor.visit(tree);
    }
}
