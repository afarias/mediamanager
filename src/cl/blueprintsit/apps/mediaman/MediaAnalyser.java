package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.utils.parser.NoDateFoundException;
import cl.blueprintsit.utils.parser.StringParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MediaAnalyser {

    private static final Logger logger = LoggerFactory.getLogger(MediaAnalyser.class);

    /* The item to be analized */
    private MediaItem mediaItem;

    public MediaAnalyser(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public MediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    /**
     * This method is responsible for parsing the release date from the folder name.
     *
     * @return The date object that represents the release date.
     */
    public Date getReleaseDate() throws NoDateFoundException {
        File containerFolder = mediaItem.getContainerFolder();
        return StringParser.extractDate(containerFolder.getName());
    }

    /**
     * This method is responsible for refreshing the current library. For starting, the process will create a tree of
     * media.
     */
    public void refresh() {

        /* The elements contained in the media item are analyzed. They can be a <em>media container</em> (folders with many media item) o just one media item (which can be a folder with many media)... */
        List<MediaItem> foundItems = MediaAnalyser.scanForItems(mediaItem);
        logger.info(foundItems.size() + " items found on the Library (" + mediaItem + ")");

        this.mediaItem.updateItems(foundItems);
    }

    /**
     * Este método es responsable de revisar un directorio de manera recursiva y recuperar el contindo.
     *
     * @return Una lista de items.
     */
    public static List<MediaItem> scanForItems(MediaItem mediaItem) {

        /* All the files on the folder are retrieved */
        File containerFolder = mediaItem.getContainerFolder();
        if (!containerFolder.isDirectory()) {
            return Collections.emptyList();
        }

        File[] files = containerFolder.listFiles();
        if (files == null) {
            return Collections.emptyList();
        }

        List<MediaItem> foundItems = new ArrayList<MediaItem>();
        for (File file : files) {
            logger.debug("Item found: " + file.getPath());

            /* TODO: By now, every item is a MediaItem */
            MediaItem anItem = new MediaItem(file);
            foundItems.add(anItem);

            /* If it is a folder, its contained elements are added */
            if (file.isDirectory()) {
                foundItems.addAll(scanForItems(anItem));
            }
        }

        return foundItems;
    }
}
