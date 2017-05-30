package cl.blueprintsit.apps.mediaman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A library is a group of media located on a disk.
 */
public class Library {

    /** The Logger for the class */
    private static final Logger logger = LoggerFactory.getLogger(Library.class);

    /** The name given to the library */
    private final String name;

    /** The path on the FS where the library is located */
    private File libraryHomePath;

    /** The media items contained on the library */
    private List<MediaItem> mediaItems;

    public Library(File libraryRootPath) {
        this.libraryHomePath = libraryRootPath;
        this.mediaItems = new ArrayList<MediaItem>();
        this.name = libraryRootPath.getName();

        /* Items are initialized */
        initializeMediaItems();
    }

    private void initializeMediaItems() {
        List<MediaItem> scannedItems = MediaAnalyser.scanForItems(new MediaItem(this.libraryHomePath));
        mediaItems.addAll(scannedItems);
        logger.debug(scannedItems.size() + " have been added to " + this.getName() + " library.");
        logger.debug(scannedItems.size() + " in total in " + this.getName() + " library.");
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }

    public String getName() {
        return name;
    }
}
