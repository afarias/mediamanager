package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.MediaAnalyser;
import cl.blueprintsit.apps.mediaman.Ranking;
import cl.blueprintsit.utils.parser.NoDateFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cl.blueprintsit.apps.mediaman.Ranking.NONE;

/**
 * A Media Item represents a media content: a movie, a scene, etc.
 * It can be either a single file, or a folder containing several others media items or media folders.
 *
 * @author Andrés Farías.
 * @see Scene
 */
public abstract class MediaItem {

    private static final Logger logger = LoggerFactory.getLogger(MediaItem.class);

    /* The physical place for where this item is located */
    private File itemFile;

    private Ranking ranking;

    /** The list of media items contained on this media Item */
    private List<MediaItem> mediaItems;

    /**
     * The default constructor that initialize some fields.
     */
    MediaItem(File itemFile) {
        this.itemFile = itemFile;
        this.mediaItems = new ArrayList<>();
        this.ranking = NONE;
    }

    /**
     * A more complex constructor receiving both the media file and its children media items.
     *
     * @param itemFile   The media file.
     * @param mediaItems The mediaItem's children.
     */
    public MediaItem(File itemFile, List<MediaItem> mediaItems) {
        this(itemFile);

        this.mediaItems.addAll(mediaItems);
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public File getContainerFolder() {
        return itemFile;
    }

    public File getItemFile() {
        return itemFile;
    }

    public List<MediaItem> getChildrenMediaItems() {
        return mediaItems;
    }

    /**
     * This method is responsible for consolidating the date of this media item to the FileSystem.
     */
    public void consolidateDates() {

        MediaAnalyser mediaAnalyser = new MediaAnalyser(this);
        Date releaseDate;
        try {
            releaseDate = mediaAnalyser.getReleaseDate();
            logger.debug("Date found for item " + this.itemFile + ": " + releaseDate);
        } catch (NoDateFoundException e) {
            logger.debug("No date found for item:" + this.itemFile);
            return;
        }

        /* Now that we have the item date, we set it as it's creation date */
        File theFile = new File(this.itemFile.getAbsolutePath());
        boolean b = theFile.setLastModified(releaseDate.getTime());
        if (b) {
            logger.info("Item " + this.toString() + ": Last modified date set to " + releaseDate);
        }
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "itemFile=" + itemFile +
                '}';
    }

    /**
     * This method is responsible for determining if this media item is a scene or not.
     *
     * @return <code>true</code> if this item is scene and <code>false</code> otherwise.
     */
    public abstract boolean isScene();
}
