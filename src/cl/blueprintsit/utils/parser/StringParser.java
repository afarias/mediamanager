package cl.blueprintsit.utils.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class StringParser {

    private static final Logger logger = LoggerFactory.getLogger(StringParser.class);

    private static final String YEAR_PATTER = "\\d\\d\\d\\d";
    private static final String DAY_AND_MONTH_PATTER = "\\d\\d";
    private static final String SEPARATOR_PATTERN = "(\\-|\\.|\\s)";

    /**
     * This method is responsible for extracting a date from a text.
     *
     * @param text The text from where the date is extracted.
     *
     * @return A <code>Date</code> object representing the extracted date.
     *
     * @throws NoDateFoundException If there was no Date to extract.
     */
    public static Date extractDate(String text) throws NoDateFoundException {

        Pattern yearOnlyPattern = Pattern.compile("\\[" + YEAR_PATTER + "\\]");

        /* PATTERN: [YYYY] The year is the most general kind of date */
        Matcher yearMatcher = yearOnlyPattern.matcher(text);
        if (yearMatcher.find()) {
            logger.debug("Patter found: " + text);

            String group = yearMatcher.group();
            Integer year = Integer.parseInt(group.substring(1, 5));

            Calendar calendar = Calendar.getInstance();

            Calendar today = Calendar.getInstance();
            today.setTime(new Date());

            if (year == today.get(YEAR)) {
                calendar.set(year, Calendar.DECEMBER, 31);
            } else {
                calendar.set(year, today.get(MONTH), 1);
            }

            return calendar.getTime();
        }

        /* PATTERN: [YYYY-MM] The year is the most general kind of date */
        Pattern yearMonthPattern = Pattern.compile("\\[" + YEAR_PATTER + SEPARATOR_PATTERN + DAY_AND_MONTH_PATTER + "\\]");
        Matcher yearMonthMatcher = yearMonthPattern.matcher(text);
        if (yearMonthMatcher.find()) {
            logger.debug("Patter found: " + text);

            String group = yearMonthMatcher.group();
            Integer year = Integer.parseInt(group.substring(1, 5));
            Integer month = Integer.parseInt(group.substring(6, 8));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, 29);

            return calendar.getTime();
        }

        /* PATTERN: [YYYY-MM-DD] A full date */
        Pattern fullDatePattern = Pattern.compile("\\[" + YEAR_PATTER + SEPARATOR_PATTERN + DAY_AND_MONTH_PATTER + SEPARATOR_PATTERN + DAY_AND_MONTH_PATTER + "\\]");
        Matcher fullDateMatcher = fullDatePattern.matcher(text);
        if (fullDateMatcher.find()) {
            logger.debug("Patter found: " + text);

            String group = fullDateMatcher.group();
            Integer year = Integer.parseInt(group.substring(1, 5));
            Integer month = Integer.parseInt(group.substring(6, 8));
            Integer day = Integer.parseInt(group.substring(9, 11));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day);

            return calendar.getTime();
        }

        /* PATTERN: [DD.MM.YYYY] and [MM.DD.YYYY] A full date */
        Pattern usDatePattern = Pattern.compile("\\["+DAY_AND_MONTH_PATTER+SEPARATOR_PATTERN + DAY_AND_MONTH_PATTER + SEPARATOR_PATTERN + YEAR_PATTER + "\\]");
        Matcher usDateMatcher = usDatePattern.matcher(text);
        if (usDateMatcher.find()) {

            String group = usDateMatcher.group();
            logger.debug("Patter found: " + group);

            Integer monthOrDay1 = Integer.parseInt(group.substring(1, 3));
            Integer monthOrDay2 = Integer.parseInt(group.substring(4, 6));
            Integer year = Integer.parseInt(group.substring(7, 11));
            Calendar calendar = Calendar.getInstance();

            if ((monthOrDay1 > 12) && (monthOrDay2 <=12)) {
                calendar.set(year, monthOrDay2 - 1, monthOrDay1);
            } else if ((monthOrDay2 > 12)) {
                calendar.set(year, monthOrDay1 - 1, monthOrDay2);
            } else {
                calendar.set(year, monthOrDay2 - 1, monthOrDay1);
            }

            return calendar.getTime();
        }


        /* PATTERN: [YY.MM.DD] */
        usDatePattern = Pattern.compile("\\["+DAY_AND_MONTH_PATTER+SEPARATOR_PATTERN + DAY_AND_MONTH_PATTER + SEPARATOR_PATTERN + DAY_AND_MONTH_PATTER + "\\]");
        usDateMatcher = usDatePattern.matcher(text);
        if (usDateMatcher.find()) {

            String group = usDateMatcher.group();
            logger.debug("Patter found: " + group);

            Integer monthOrDay1 = Integer.parseInt(group.substring(1, 3));
            Integer monthOrDay2 = Integer.parseInt(group.substring(4, 6));
            Integer monthOrDay3 = Integer.parseInt(group.substring(7, 9));
            Calendar calendar = Calendar.getInstance();

            /* The year is first here: */
            if ((monthOrDay1 > 12) && (monthOrDay2 <=12) && monthOrDay3 > 12) {
                calendar.set(2000 + monthOrDay1, monthOrDay2 - 1, monthOrDay3);
            }

            return calendar.getTime();
        }

        throw new NoDateFoundException();
    }
}
