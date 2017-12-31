package cl.blueprintsit.apps.mediaman.analyser.fixers;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaContainer;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaFilm;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaSceneFile;
import cl.blueprintsit.apps.mediaman.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cl.blueprintsit.apps.mediaman.model.Tag.TO_BE_SEEN;

/**
 * @author Andrés Farías on 6/17/17.
 */
public class ToBeSeenConsolidator implements IMediaVisitor {

    private static final Logger logger = LoggerFactory.getLogger(MediaItem.class);

    /**
     * When visiting a media container, the folder name is analysed for the "2C" tag. If it does contain it, then it is
     * removed, because containers should not have the tag.
     * The media container do not have the "2C" tag, so it only makes fix on its children.
     *
     * @param mediaContainer The media container to be analysed.
     *
     * @return The number of children fixed.
     */
    @Override
    public int visit(MediaContainer mediaContainer) {

        /* If the tag IS THERE then it is removed */
        if (mediaContainer.containsTagValue("2C", false)) {
            mediaContainer.removeTagsWithValue("2C");
            mediaContainer.removeTagsWithValue("2c");
        }

        /* It then visits the children */
        int counter = 0;
        for (MediaItem mediaItem : mediaContainer.getChildrenMediaItems()) {
            counter += mediaItem.visit(this);
        }

        return counter;
    }


    /**
     * This method is responsible for modifying the media item if it should be marked to be seen. A film may have
     * scenes
     * so, before processing this film, its children are to be processed first.
     *
     * @param mediaFilm The media film to be verified for the 2C tag.
     *
     * @return The number of media items corrected.
     */
    @Override
    public int visit(MediaFilm mediaFilm) {

        /* First is to check the children tags [2C] */
        int count = 0;
        for (MediaItem child : mediaFilm.getChildrenMediaItems()) {
            count += child.visit(this);
        }

        /* First is to check if it has to be seen (it has some un-ranked media items) */
        if (!mediaFilm.hasRanking() && mediaFilm.isToBeSeen()) {
            mediaFilm.addTag(TO_BE_SEEN);
            count++;
        } else if (!mediaFilm.containsTag(TO_BE_SEEN, false) && !mediaFilm.isToBeSeen()){
            mediaFilm.removeTagsWithValue("2C");
        } else {
            logger.debug("Nothing to do");
        }

        mediaFilm.consolidateTags();
        return count;
    }

    /**
     * TODO: complete this method.
     *
     * @param sceneFile A scene file to be scanned.
     *
     * @return The number of changes performed media scenes processed.
     */
    @Override
    public int visit(MediaSceneFile sceneFile) {
        return 0;
    }
}