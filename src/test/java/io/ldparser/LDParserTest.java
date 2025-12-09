package io.ldparser;

import org.junit.Test;
import java.io.ByteArrayInputStream;
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
        // TODO
        // assertTrue(cmd.getExpression() instanceof NumberExpression);
        // assertEquals(42, ((NumberExpression) cmd.getExpression()).getValue());
    }
}
