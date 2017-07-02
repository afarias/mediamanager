package cl.blueprintsit.utils;

import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import cl.blueprintsit.apps.mediaman.model.Tag;
import cl.blueprintsit.utils.files.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
     * This method is responsible for parsing a line and returning the token value at a specific position.
     *
     * @param line     The line to be parsed to extract the log.
     * @param position The token position on the line.
     *
     * @return The token's vale.
     */
    public Tag extractTagAtPosition(String line, int position) {

        List<Tag> tags = extractTagsFromLine(line);

        /* The token at the specified position is returned (only if there are enough tokens) */
        if (position <= tags.size()) {
            return tags.get(position - 1);
        }

        /* Otherwise, there were less tokens than the asked position */
        throw new IllegalArgumentException("No that so many tokens!");
    }

    /**
     * This method is responsible to retrieving all the tokens from a line.
     *
     * @param line The line from which the tokens are retrieved.
     *             TODO: Test this method.
     *
     * @return A list of tokens extracted from the line.
     */
    public List<Tag> extractTagsFromLine(String line) {

        if (line.length() <= 2) {
            return Collections.emptyList();
        }

        List<Tag> tokens = new ArrayList<>();

        int index, last = 0, loop = 0;
        while ((index = line.indexOf(this.initDelimiter, last)) != -1) {

            /* If the END_TOKEN is found, a token has been found */
            if ((last = line.indexOf(this.endDelimiter, index)) != -1) {
                tokens.add(new Tag(line.substring(index + 1, last), null));
            } else {
                return tokens;
            }
        }

        return tokens;
    }

    /**
     * This method is responsible for consolidating the file with respect to the tag removed.
     *
     * @param tagValue  The tag to be removed.
     * @param mediaItem The media item from which the tag is removed.
     *
     * @return <code>true</code> if the tag is removed and <code>false</code> otherwise.
     */
    public boolean removeTagFromFile(String tagValue, MediaItem mediaItem) {

        File itemFile = mediaItem.getItemFile();
        String fileName = itemFile.getName();

        String newFileName = fileName.replaceAll("\\" + initDelimiter + tagValue + "\\" + endDelimiter, "");
        return FileUtils.renameFileName(itemFile, newFileName);
    }

    /**
     * This method is responsible for removing every Tag within the given <code>text</code>.
     *
     * @param text The text given from which the tags are to be removed.
     *
     * @return The same text, with no tags.
     */
    public String removeTagsFromText(String text) {

        /* The tags are extracted */
        List<Tag> tags = extractTagsFromLine(text);
        String result = text;
        for (Tag tag : tags) {
            result = result.replace(initDelimiter + tag.getValue() + endDelimiter, "");
        }

        return result;
    }

    public String appendTags(String text, List<Tag> tags) {

        StringBuilder result = new StringBuilder(text);
        for (Tag tag : tags) {
            result.append(initDelimiter).append(tag.getValue()).append(endDelimiter);
        }

        return result.toString();
    }
}
