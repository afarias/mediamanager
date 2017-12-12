package cl.blueprintsit.utils;

import cl.blueprintsit.apps.mediaman.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class aims to provide basic functionality for handle tokens.
 *
 * @author Andrés Farías on 6/1/17.
 */
public class TagUtils {

    private static final Logger logger = LoggerFactory.getLogger(TagUtils.class);

    /* Initial token's delimiter */
    private String initDelimiter;

    /* Final token's delimiter */
    private String endDelimiter;

    /**
     * The more basic constructor requires the token's delimiters to be defined.
     *
     * @param initDelimiter Initial token's delimiter
     * @param endDelimiter  Final token's delimiter
     */
    public TagUtils(String initDelimiter, String endDelimiter) {
        this.initDelimiter = initDelimiter;
        this.endDelimiter = endDelimiter;
    }

    public String getInitDelimiter() {
        return initDelimiter;
    }

    public String getEndDelimiter() {
        return endDelimiter;
    }

    /**
     * This method is responsible for determining if the given text has a tag (any).
     *
     * @param text The text to be analysed.
     *
     * @return <code>true</code> if the text has a tag, and <code>false</code> otherwise.
     */
    protected boolean hasTag(String text) {

        /* First is to determine if the text has an init delimiter */
        int startIndex = text.indexOf(initDelimiter);
        if (startIndex == -1) {
            return false;
        }

        return text.indexOf(endDelimiter, startIndex) >= 0;
    }

    /**
     * This method is responsible to retrieving the first <i>Tag</i> from a text (from left to right) without removing
     * it from the text.
     *
     * @param text The text from which the <i>next</i> token.
     *
     * @return A list of tokens extracted from the line.
     *
     * @throws java.lang.IllegalArgumentException Thrown if there is no tag in the text.
     */
    public Tag getNextTag(String text) {

        if (text.length() <= 2) {
            throw new IllegalArgumentException("The text contains no tag.");
        }

        int index = text.indexOf(this.initDelimiter);
        if (index == -1) {
            throw new IllegalArgumentException("The text contains no tag.");
        }

        /* If the END_TOKEN is found, a token has been found */
        int last = text.indexOf(this.endDelimiter, index);
        if (last == -1) {
            throw new IllegalArgumentException("The text contains no tag.");
        }


        Tag extractedTag = new Tag(text.substring(index + 1, last), null);
        logger.debug("Obtained tag from text {}: {}", text, extractedTag);

        return extractedTag;
    }

    /**
     * This method is responsible for obtaining all the tags within a given <code>text</code>. Similar to the method
     * <code>getNextTag</code> method, but returning all tags on the text.
     *
     * @param text The text from which the tags are retrieved.
     *
     * @return A list of all the retrieved tags.
     */
    public List<Tag> getTags(String text) {

        List<Tag> tags = new ArrayList<>();
        StringBuilder updatedText = new StringBuilder(text);
        while (hasTag(updatedText.toString())) {
            Tag nextTag = getNextTag(updatedText.toString());
            tags.add(nextTag);
            String theTag = initDelimiter + nextTag.getValue() + endDelimiter;
            int start = updatedText.indexOf(theTag);
            updatedText.replace(start, start + theTag.length(), "");
        }

        return tags;
    }

    /**
     * This method is responsible to retrieving and extracting all the tokens from a given <code>text</code>. Once the
     * tags has been extracted, the line do not contain them any more.
     *
     * @param text The text from which the tokens are extracted.
     *             a    *
     *
     * @return The text (a new fresh String) without the removed tags from it.
     */
    public List<Tag> extractTags(String text) {

        List<Tag> tokens = new ArrayList<>();
        StringBuilder updatedText = new StringBuilder(text);

        /* variable 'last' has a zero value for starting */
        int index, last;
        while (hasTag(updatedText.toString())) {
            index = updatedText.indexOf(this.initDelimiter);
            last = updatedText.indexOf(this.endDelimiter, index);

            Tag extractedTag = new Tag(updatedText.substring(index + 1, last), null);
            tokens.add(extractedTag);
            logger.debug("Tag extracted from '{}': {}", text, extractedTag);

            updatedText.replace(index, last, "");
        }

        logger.debug("{} tags were extracted from text '{}'", tokens.size(), text);
        return tokens;
    }

    /**
     * This method is responsible for removing every Tag within the given <code>text</code> and return the tag-less
     * text.
     *
     * @param text The text given from which the tags are to be removed.
     *
     * @return The same text, with no tags.
     */
    public String removeTags(String text) {

        List<Tag> tokens = new ArrayList<>();
        StringBuilder updatedText = new StringBuilder(text);

        /* variable 'last' has a zero value for starting */
        int index, last;
        while (hasTag(updatedText.toString())) {
            index = updatedText.indexOf(this.initDelimiter);
            last = updatedText.indexOf(this.endDelimiter, index);

            Tag extractedTag = new Tag(updatedText.substring(index + 1, last), null);
            tokens.add(extractedTag);
            logger.debug("Tag extracted from '{}': {}", text, extractedTag);

            updatedText.replace(index, last + 1, "");
        }

        logger.debug("{} tags were extracted from text '{}'", tokens.size(), text);
        return updatedText.toString();
    }

    /**
     * This method is responsible for appending tags to the end of the text.
     *
     * @param text The text to which the tags are appended.
     * @param tags The tags to be appended.
     *
     * @return A new text with the appended tags.
     */
    public String appendTags(String text, List<Tag> tags) {

        StringBuilder result = new StringBuilder(text);
        for (Tag tag : tags) {
            result.append(" ").append(initDelimiter).append(tag.getValue()).append(endDelimiter);
        }

        return result.toString();
    }
}
