package cl.blueprintsit.utils.dates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrés Farías on 6/7/17.
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /** A map for storing all kind of dates */
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{4}-\\d{2}-[13-31]\\s$", "yyyy-MM-dd");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s$", "yyyy-dd-MM");
        put("^[a-z]{3}\\s{1,}\\d{1,2},\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s(AM|PM)", "MMM dd, yyyy HH:mm:ss");
        put("^[a-z]{3}\\s{1,}\\d{1,2},\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s(AM|PM)\\s[a-z]{1,4}", "MMM dd, yyyy HH:mm:ss"); //
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{4}$", "yyyy");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{4}-\\d{1,2}$", "yyyy-MM");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
    }};

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     *
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     *
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     *
     * @see java.text.SimpleDateFormat
     */
    public static String determineDateFormat(String dateString) {

        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp.toLowerCase())) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }

        return null;
    }

    /**
     * This method is responsible for determining the regex expression for a given date, and then parse it to return a
     * valid Date object.
     *
     * @param dateString The date to be parsed.
     *
     * @return An object representing the date given.
     */
    public static Date format(String dateString) {

        if (dateString == null) {
            logger.error("Null Date when one is required");
            throw new IllegalArgumentException("Null Date when one is required");
        }

        String regex = determineDateFormat(dateString);
        if (regex == null) {
            logger.error("Regex is null when one is required");
            throw new IllegalArgumentException("Regex is null when one is required");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(regex);
        Date parse = null;
        try {
            parse = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parse;
    }
}
