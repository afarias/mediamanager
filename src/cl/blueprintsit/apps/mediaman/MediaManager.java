package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.apps.mediaman.analyser.DateConsolidator;
import cl.blueprintsit.apps.mediaman.analyser.TagCorrecter;
import cl.blueprintsit.apps.mediaman.analyser.YearCorrecter;
import cl.blueprintsit.apps.mediaman.analyser.fixers.ToBeSeenConsolidator;
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

    @Override
    public int consolidateToBeSeen(MediaItem mediaITem) {
        return mediaITem.visit(new ToBeSeenConsolidator());
    }
}
