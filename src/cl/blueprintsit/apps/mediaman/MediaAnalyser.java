package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.utils.parser.NoDateFoundException;
import cl.blueprintsit.utils.parser.StringParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MediaAnalyser {

    /* The item to be analized */
    private MediaItem mediaItem;

    public MediaAnalyser(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    /**
     * This method is responsible for parsing the release date from the folder name.
     *
     * @return The date object that represents the release date.
     */
    public Date getReleaseDate() {
        File containerFolder = mediaItem.getContainerFolder();
        StringParser parser = new StringParser();
        try {
            StringParser.extractDate(containerFolder.getName());
        } catch (NoDateFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;  //TODO: Terminar esto
    }

    /**
     * This method is responsible for refreshing the current library. For starting, the process will create a tree of
     * media.
     */
    public void refresh() {

        /* The elements contained in the media item are analyzed. They can be a <em>media container</em> (folders with many media item) o just one media item (which can be a folder with many media)... */
        List<MediaItem> foundItems = MediaAnalyser.scanForItems(mediaItem);
        System.out.println(foundItems.size() + " items found on the Library (" + mediaItem + ")");

        this.mediaItem.updateItems(foundItems);
    }

    /**
     * Este método es responsable de revisar un directorio de manera recursiva y recuperar el contindo.
     *
     * @return Una lista de items.
     */
    private static List<MediaItem> scanForItems(MediaItem mediaItem) {

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
            System.out.println("Item found: " + file.getPath());

            /* TODO: By now, every item is a MediaItem */
            MediaItem anItem = new MediaItem(file);
            foundItems.add(anItem);

            /* If it is a folder, its contained elements are added */
            if (file.isDirectory()){
                foundItems.addAll(scanForItems(anItem));
            }
        }

        return foundItems;
    }
}
