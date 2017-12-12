package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.apps.mediaman.analyser.DateConsolidator;
import cl.blueprintsit.apps.mediaman.analyser.TagCorrecter;
import cl.blueprintsit.apps.mediaman.analyser.fixers.ToBeSeenConsolidator;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
     * This method is where the visitors are applyed.
     *
     * @param mediaITem The library to be managed with 2C.
     *
     * @return The number of children visited.
     */
    @Override
    public int consolidateToBeSeen(MediaItem mediaITem) {
        return mediaITem.visit(new ToBeSeenConsolidator());
    }

    @Override
    public int fixTags(MediaItem library) {
        return library.visit(new TagCorrecter());
    }
}
