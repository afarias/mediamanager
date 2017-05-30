package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.utils.parser.NoDateFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A Media Item represents a media content: a movie, a scene, etc.
 * It can be either a single file, or a folder containing several others.
 */
public class MediaItem {

    private static final Logger logger = LoggerFactory.getLogger(MediaItem.class);

    /* The physical place for where this item is located */
    private File itemPath;

    /** The media item list contained in this media item */
    private List<MediaItem> mediaItems;

    /**
     * The default constructor that initialize some fields.
     */
    private MediaItem() {
        this.mediaItems = new ArrayList<MediaItem>();
    }

    public MediaItem(File itemPath) {
        this();
        this.itemPath = itemPath;
    }

    public File getContainerFolder() {
        return itemPath;
    }

    /**
     * This method is responsible for consolidating the date of this media item to the FileSystem.
     */
    public void consolidateDates() {

        MediaAnalyser mediaAnalyser = new MediaAnalyser(this);
        Date releaseDate;
        try {
            releaseDate = mediaAnalyser.getReleaseDate();
            logger.debug("Date found for item " + this.itemPath + ": " + releaseDate);
        } catch (NoDateFoundException e) {
            logger.debug("No date found for item:" + this.itemPath);
            return;
        }

        /* Now that we have the item date, we set it as it's creation date */
        File theFile = new File(this.itemPath.getAbsolutePath());
        boolean b = theFile.setLastModified(releaseDate.getTime());
        if (b){
            logger.info("Item " + this.toString() + ": Last modified date set to " + releaseDate);
        }
    }

    public void updateItems(List<MediaItem> items) {
        this.mediaItems.addAll(items);
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "itemPath=" + itemPath +
                '}';
    }
}
