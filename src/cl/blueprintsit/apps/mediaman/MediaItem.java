package cl.blueprintsit.apps.mediaman;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A Media Item represents a media content: a movie, a scene, etc.
 * It can be either a single file, or a folder containing several others.
 */
public class MediaItem {

    /* The phisical place for where this item is located */
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

    public void consolidateDates() {

        MediaAnalyser mediaAnalyser = new MediaAnalyser(this);
        Date releaseDate = mediaAnalyser.getReleaseDate();

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
