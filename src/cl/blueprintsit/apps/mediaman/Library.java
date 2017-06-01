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
    private final File libraryHomePath;

    /** The media items contained on the library */
    private List<MediaItem> mediaItems;

    /**
     * Default constructor.
     *
     * @param libraryRootPath The file system path of the library root folder.
     */
    public Library(File libraryRootPath) {
        this.libraryHomePath = libraryRootPath;
        this.mediaItems = new ArrayList<MediaItem>();
        this.name = libraryRootPath.getName();
    }

    public File getLibraryHomePath() {
        return libraryHomePath;
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public String getName() {
        return name;
    }

    public void addMediaItems(MediaItem item) {
        this.mediaItems.add(item);
    }

    /**
     * This method is responsible for counting the number of media items contained on the library.
     * @return The number of items contained in this library.
     */
    public int getSize() {

        int total = 0;
        for (MediaItem mediaItem : mediaItems) {
            total++; // current item is counted
            total += mediaItem.getSize();
        }

        return total;
    }
}
