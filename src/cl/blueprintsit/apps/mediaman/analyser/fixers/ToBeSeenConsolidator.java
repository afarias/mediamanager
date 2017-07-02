package cl.blueprintsit.apps.mediaman.analyser.fixers;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaContainer;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaFilm;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import cl.blueprintsit.apps.mediaman.mediaitem.SceneFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static cl.blueprintsit.apps.mediaman.Ranking.NONE;
import static cl.blueprintsit.apps.mediaman.mediaitem.MediaFactory.parseRanking;

/**
 * @author Andrés Farías on 6/17/17.
 */
public class ToBeSeenConsolidator implements IMediaVisitor {

    private static final Logger logger = LoggerFactory.getLogger(MediaItem.class);

    /**
     * The media container do not have the "2C" tag, so it only makes fix on its children.
     *
     * @param mediaContainer The media container.
     *
     * @return The number of children fixed.
     */
    @Override
    public int visit(MediaContainer mediaContainer) {

        /* If the tag IS THERE then it is removed */
        if (mediaContainer.containsTagValue("2C") || mediaContainer.containsTagValue("2c")) {
            mediaContainer.removeTagsWithValue("2C");
            mediaContainer.removeTagsWithValue("2c");
        }

        /* It then visits the children */
        int counter = 0;
        for (MediaItem mediaItem : mediaContainer.getChildrenMediaItems()) {
            counter = mediaItem.visit(this);
        }

        return counter;
    }


    /**
     * This method is responsible for modifying the media item if it should be marked to be seen.
     *
     * @param mediaFilm The media film to be verified for the 2C tag.
     *
     * @return The number of media items corrected.
     */
    @Override
    public int visit(MediaFilm mediaFilm) {

        /* Are we dealing with a folder or a file? */
        int count = 0;

        /* If the tag IS THERE then it is removed */
        if (parseRanking(mediaFilm).equals(NONE)) {

            File itemFile = mediaFilm.getItemFile();
            mediaFilm.removeTagsWithValue("2C");
            boolean renamed = itemFile.renameTo(new File(itemFile.getAbsolutePath() + " [2C]"));
            if (renamed) {
                logger.info("File renamed to be seen: " + itemFile.getName());
                count++;
            }
        }

        /* Recursion for the children... */
        for (MediaItem child : mediaFilm.getChildrenMediaItems()) {
            count += child.visit(this);
        }

        return count;
    }

    @Override
    public int visit(SceneFile sceneFile) {
        return 0;
    }
}