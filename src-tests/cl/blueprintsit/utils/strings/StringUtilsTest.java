package cl.blueprintsit.utils.strings;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

    @Test
    public void testContainsAny01() throws Exception {
        assertTrue(StringUtils.containsAny("TXT", "TXT"));
    }

    @Test
    public void testContainsAny02() throws Exception {
        assertTrue(StringUtils.containsAny("TXT", "TXT", "ADN"));
    }

    @Test
    public void testContainsAny03() throws Exception {
        assertTrue(StringUtils.containsAny("TXTADN", "TXT", "ADN"));
    }

    @Test
    public void testContainsAny04() throws Exception {
        assertFalse(StringUtils.containsAny("", "TXT", "ADN"));
    }

    @Test
    public void testContainsAny05() throws Exception {
        assertFalse(StringUtils.containsAny("TEXT", "TXT", "ADN"));
    }
}