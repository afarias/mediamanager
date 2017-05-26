package cl.blueprintsit.utils.parser;

import org.junit.Test;

import java.util.Date;

public class StringParserTest {

    @Test
    public void testExtractDate() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[2000]");
    }
}
