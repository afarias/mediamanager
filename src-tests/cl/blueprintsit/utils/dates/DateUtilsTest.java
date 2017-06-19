package cl.blueprintsit.utils.dates;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DateUtilsTest {

    @Test
    public void testFormat() throws Exception {

        Date date = DateUtils.format("2017-02-13");

        assertNotNull(date);

        Calendar instance = Calendar.getInstance();
        instance.setTime(date);

        int year = instance.get(YEAR);
        assertEquals(2017, year);

        int month = instance.get(MONTH);
        assertEquals(FEBRUARY, month);

        int day = instance.get(DAY_OF_MONTH);
        assertEquals(13, day);
    }
}