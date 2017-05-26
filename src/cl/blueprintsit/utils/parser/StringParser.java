package cl.blueprintsit.utils.parser;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {

    public static Date extractDate(String matcher) throws NoDateFoundException {
        Pattern yearPattern = Pattern.compile("\\[\\d\\d\\d\\d\\]");
        Matcher yearMatcher = yearPattern.matcher(matcher);

        /* The year is the most general kind of date */
        if (!yearMatcher.find()){
            throw new NoDateFoundException();
        }

        String group = yearMatcher.group();
        System.out.println("Date found: " + group);

        return new Date();
    }

}
