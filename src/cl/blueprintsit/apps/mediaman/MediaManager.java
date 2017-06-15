package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.apps.mediaman.analyser.DateConsolidator;
import cl.blueprintsit.apps.mediaman.analyser.TagCorrecter;
import cl.blueprintsit.apps.mediaman.analyser.YearCorrecter;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

import static cl.blueprintsit.apps.mediaman.Ranking.NONE;
import static cl.blueprintsit.apps.mediaman.mediaitem.MediaFactory.parseRanking;

public class MediaManager implements IMediaManager {

    private static final Logger logger = LoggerFactory.getLogger(MediaManager.class);

    @Override
    public void organizeLibrary(MediaItem mediaItem) {

        /* The library is iterated through it's media items in order to perform the organization */
        DateConsolidator dateConsolidator = new DateConsolidator();
        List<MediaItem> mediaItemsModified = dateConsolidator.consolidateDates(mediaItem, true);

        logger.info("The number of items whose date was consolidated is {}", mediaItemsModified.size());
    }

    /**
     * This method is responsible for modifying the media item if it should be marked to be seen.
     *
     * @return <code>true</code> if the item was change to be seen, or <code>false</code> if not.
     */
    public boolean consolidateToBeSeen(MediaItem mediaItem) {

        /* Are we dealing with a folder or a file? */
        if (parseRanking(mediaItem).equals(NONE)) {
            File itemFile = mediaItem.getItemFile();
            boolean renamed = itemFile.renameTo(new File(itemFile.getAbsolutePath() + " [2C]"));
            if (renamed) {
                logger.info("File renamed: " + itemFile.getName());
                return true;
            }
        }

        /* If nothing changed... */
        return false;
    }

    public void fixTags(MediaItem library) {

        int i = new YearCorrecter().repairYearInParenthesis(library);
        logger.info("Year Tag corrected for {} files", i);

        i = new TagCorrecter().fix(library);
    }
}
