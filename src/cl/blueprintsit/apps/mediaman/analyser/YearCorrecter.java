package cl.blueprintsit.apps.mediaman.analyser;

import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * This class is responsible for provide several logic to order date tags.
 *
 * @author Andrés Farías on 6/11/17.
 */
public class YearCorrecter {

    private static final Logger logger = LoggerFactory.getLogger(YearCorrecter.class);

    public int repairYearInParenthesis(MediaItem mediaItem) {

        int correctedItems = 0;
        /* Checks for (2007) file names */
        File itemFile = mediaItem.getItemFile();
        String name = itemFile.getName();
        int initIndex = name.indexOf("(");
        if (initIndex != -1) {
            if (name.charAt(initIndex + 5) == ')') {

                String year = name.substring(initIndex + 1, initIndex + 5);
                try {
                    int theYear = Integer.parseInt(year);
                    logger.debug("Date found to fix!: {}", name);

                    String newName = name.replace("(" + theYear + ")", "[" + theYear + "]");
                    String absolutePath = itemFile.getParentFile().getAbsolutePath();
                    File destination = new File(absolutePath + "/" + newName);
                    boolean b = itemFile.renameTo(destination);

                    if (b) {
                        logger.info("Media item year tag corrected: " + mediaItem.toString());
                        correctedItems++;
                    }
                } catch (NumberFormatException nfe) {
                    logger.debug("Wrong finding!");
                }
            }
        }

        for (MediaItem item : mediaItem.getChildrenMediaItems()) {
            correctedItems += repairYearInParenthesis(item);
        }

        return correctedItems;
    }
}
