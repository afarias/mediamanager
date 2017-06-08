package cl.blueprintsit.utils.strings;

/**
 * @author Andrés Farías on 6/5/17.
 */
public class StringUtils {
    /**
     * This method is responsible for determining if any of the given <code>words</code> is contained on the text.
     *
     * @param text  The text to be tested to contain any given word.
     * @param words The words to be checked.
     *
     * @return <code>true</code> if the <code>text</code> contains any of the given <code>words</code>.
     */
    public static boolean containsAny(String text, String... words) {

        for (String word : words) {
            if (text.toUpperCase().contains(word.toUpperCase())) return true;
        }

        return false;
    }
}
