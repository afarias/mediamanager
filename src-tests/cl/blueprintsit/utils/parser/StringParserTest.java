package cl.blueprintsit.utils.parser;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StringParserTest {

    private static final Logger logger = LoggerFactory.getLogger(StringParserTest.class);

    @Test
    public void testExtractDate01() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[2000]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(calendar.get(YEAR), 2000);
    }

    @Test
    public void testExtractDate02() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[2001]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(calendar.get(YEAR), 2001);
    }

    @Test
    public void testExtractDateMonth01() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[2001-01]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(2001, calendar.get(YEAR));
        assertEquals(JANUARY, calendar.get(MONTH));
    }

    @Test
    public void testExtractDateMonth02() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[1999-12]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(1999, calendar.get(YEAR));
        assertEquals(DECEMBER, calendar.get(MONTH));
    }

    @Test
    public void testExtractFullDate01() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[1999-12-01]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(1999, calendar.get(YEAR));
        assertEquals(DECEMBER, calendar.get(MONTH));
        assertEquals(1, calendar.get(DAY_OF_MONTH));
    }

    @Test
    public void testExtractFullDate02() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[1999-12-31]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(1999, calendar.get(YEAR));
        assertEquals(DECEMBER, calendar.get(MONTH));
        assertEquals(31, calendar.get(DAY_OF_MONTH));
    }

    @Test
    public void testExtractFullDate03() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[12.31.1999]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(1999, calendar.get(YEAR));
        assertEquals(DECEMBER, calendar.get(MONTH));
        assertEquals(31, calendar.get(DAY_OF_MONTH));
    }

    @Test
    public void testExtractFullDate04() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[31.12.1999]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(1999, calendar.get(YEAR));
        assertEquals(DECEMBER, calendar.get(MONTH));
        assertEquals(31, calendar.get(DAY_OF_MONTH));
    }

    @Test
    public void testExtractFullDate05() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[01.02.1975]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(1975, calendar.get(YEAR));
        assertEquals(FEBRUARY, calendar.get(MONTH));
        assertEquals(1, calendar.get(DAY_OF_MONTH));
    }

    @Test
    public void testExtractFullDate06() throws Exception, NoDateFoundException {
        Date date = StringParser.extractDate("[28.05.2017]");

        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(2017, calendar.get(YEAR));
        assertEquals(MAY, calendar.get(MONTH));
        assertEquals(28, calendar.get(DAY_OF_MONTH));
    } //[28.05.2017]

    @Test(expected = NoDateFoundException.class)
    public void testExtractDateFail01() throws Exception, NoDateFoundException {
        StringParser.extractDate("[2000a]");
    }
}
