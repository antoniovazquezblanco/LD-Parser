package io.ldparser;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;

public class LDParserTest {

    @Test
    public void testParseSimpleProvide() throws Exception {
        String script = "PROVIDE(foo = 42);";
        ByteArrayInputStream input = new ByteArrayInputStream(script.getBytes());
        Script parsed = LDParser.parse(input);
        assertNotNull(parsed);
        assertEquals(1, parsed.getCommands().size());
        assertTrue(parsed.getCommands().get(0) instanceof ProvideCommand);
        ProvideCommand cmd = (ProvideCommand) parsed.getCommands().get(0);
        assertEquals("foo", cmd.getSymbolName());
        assertNotNull(cmd.getExpression());
        assertEquals("42", cmd.getExpression().getExpressionString());
        assertEquals(42, cmd.getExpression().getNumericalValue());
    }

    @Test
    public void testParseExampleFiles() throws Exception {
        Path examplesDir = Paths.get("src", "test", "resources");
        if (!Files.exists(examplesDir)) {
            return;
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(examplesDir)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    try (InputStream input = Files.newInputStream(file)) {
                        LDParser.parse(input);
                    } catch (Exception e) {
                        fail("Failed to parse " + file + ": " + e.getMessage());
                    }
                }
            }
        }
    }
}
