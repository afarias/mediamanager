package cl.blueprintsit.apps.mediaman;

import java.io.File;

/**
 * A library is a group of media located on a disk.
 */
public class Library {

    private File libraryHomePath;
    private MediaItem[] mediaItems;

    public Library(File libraryRootPath) {

    }

    public MediaItem[] getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(MediaItem[] mediaItems) {
        this.mediaItems = mediaItems;
    }
}
