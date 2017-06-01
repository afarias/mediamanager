package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.utils.parser.NoDateFoundException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static cl.blueprintsit.apps.mediaman.MediaKind.*;
import static cl.blueprintsit.apps.mediaman.Ranking.*;

/**
 * A Media Item represents a media content: a movie, a scene, etc.
 * It can be either a single file, or a folder containing several others.
 */
public class MediaItem {

    private static final Logger logger = LoggerFactory.getLogger(MediaItem.class);

    /* The physical place for where this item is located */
    private File itemFile;

    private MediaKind kind;

    /** The media item list contained in this media item */
    private List<MediaItem> mediaItems;

    private Ranking ranking;

    /**
     * The default constructor that initialize some fields.
     */
    private MediaItem() {
        this.mediaItems = new ArrayList<MediaItem>();
        this.kind = OTHER;
        this.ranking = NONE;
    }

    public MediaItem(File itemFile) {
        this();
        this.itemFile = itemFile;

        initKind();
        initRanking();
    }

    public MediaItem(File itemFile, List<MediaItem> mediaItems) {
        this(itemFile);
        this.mediaItems = mediaItems;

        initKind();
    }

    public MediaKind getKind() {
        return kind;
    }

    public void setKind(MediaKind kind) {
        this.kind = kind;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    /**
     * This method initialize the kind of this media item.
     */
    private void initKind() {

        /* If it's a file of a certain media type... */
        if (this.itemFile.exists() && this.itemFile.isFile()) {
            String extension = FilenameUtils.getExtension(this.itemFile.getAbsolutePath());
            if (extension.equalsIgnoreCase("wmv") || extension.equalsIgnoreCase("avi")) {
                this.kind = MEDIA_VIDEO;
            }
        }

        /* If it's a directory */
        else if (this.itemFile.exists() && this.itemFile.isDirectory()) {

            /* There are two additional conditions: 1. Has a media video and 2. it does not contain MEDIA_FOLDER
             * TODO: This will be out folders with inner folders with pictures */
            if (contains(MEDIA_VIDEO) && !contains(MEDIA_FOLDER)) {
                this.kind = MEDIA_FOLDER;
            }
        }

        /* Anything else */
        else {
            this.kind = MediaKind.OTHER;
        }
    }

    /**
     * This method is responsible for initializing the Ranking media item.
     */
    private void initRanking() {

        if (this.itemFile.getName().contains("[N-10]")) {
            this.ranking = TEN;
        } else if (this.itemFile.getName().contains("[N-9]")) {
            this.ranking = NINE;
        } else if (this.itemFile.getName().contains("[N-8]")) {
            this.ranking = EIGHT;
        } else if (this.itemFile.getName().contains("[N-7]")) {
            this.ranking = SEVEN;
        } else if (this.itemFile.getName().contains("[N-6]")) {
            this.ranking = SIX;
        } else this.ranking = NONE;
    }


    /**
     * This method is responsible for determining if the current media item contains any item of the given kind.
     *
     * @param mediaKind The media kind searched for.
     *
     * @return <code>true</code> if the media item contains any item of the given kind, and <code>false</code>
     * otherwise.
     */
    private boolean contains(MediaKind mediaKind) {

        for (MediaItem mediaItem : mediaItems) {
            if (mediaItem.getKind().equals(mediaKind)) {
                return true;
            }
        }

        return false;
    }

    public File getContainerFolder() {
        return itemFile;
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

    /**
     * This method is responsible for modifying the media item if it should be marked to be seen.
     *
     * @return <code>true</code> if the item was change to be seen, or <code>false</code> if not.
     */
    public boolean consolidateToBeSeen() {

        /* Are we dealing with a folder or a file? */
        if (this.getKind().equals(MEDIA_FOLDER) && this.getRanking().equals(NONE) && !this.itemFile.getName().contains("[2C]")) {
            boolean renamed = this.itemFile.renameTo(new File(this.itemFile.getAbsolutePath() + " [2C]"));
            if (renamed) {
                logger.info("File renamed: " + this.itemFile.getName());
                return true;
            }
        }

        /* If nothing changed... */
        return false;
    }

    /**
     * This method is responsible for adding media items (folders or files) to this item.
     *
     * @param items The items to be added.
     */
    public void addMediaItems(Set<MediaItem> items) {

        if (!this.getKind().equals(MEDIA_FOLDER) && !this.getKind().equals(CONTAINER_FOLDER)) {
            throw new IllegalArgumentException("Items can't be added to media files");
        }

        this.mediaItems.addAll(items);
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "itemFile=" + itemFile +
                '}';
    }

    /**
     * This method is responsible for counting the number of media items contained on the library.
     * @return The number of items contained in this library.
     */
    public int getSize() {

        int total = 0;
        for (MediaItem mediaItem : mediaItems) {
            total++;
            total += mediaItem.getSize();
        }

        return total;
    }
}
