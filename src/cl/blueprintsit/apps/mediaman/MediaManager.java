package cl.blueprintsit.apps.mediaman;

import com.sun.istack.internal.NotNull;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cl.blueprintsit.apps.mediaman.MediaKind.CONTAINER_FOLDER;
import static cl.blueprintsit.apps.mediaman.MediaKind.MEDIA_FOLDER;
import static cl.blueprintsit.apps.mediaman.MediaKind.MEDIA_VIDEO;

public class MediaManager implements IMediaManager {

    private static final Logger logger = LoggerFactory.getLogger(MediaManager.class);

    @Override
    public void organizeLibrary(Library library) {

        /* The library is iterated through it's media items in order to perform the organization */
        for (MediaItem item : library.getMediaItems()) {

            item.consolidateDates();
        }
    }

    @Override
    public Library createLibrary(@NotNull File directoryLibrary) {

        /* Basic validations */
        if (!directoryLibrary.isDirectory()) {
            String errorMessage = "The path " + directoryLibrary.getPath() + " is not valid.";
            logger.error(errorMessage);
            throw new InvalidParameterException(errorMessage);
        }

        /* The theLibrary object is created */
        Library theLibrary = new Library(directoryLibrary);

        initializeMediaItems(theLibrary);
        return theLibrary;
    }

    /**
     * This method is responsible for adding items to the current library.
     *
     * @param theLibrary The library whose sub-items are going to be processed.
     */
    private void initializeMediaItems(Library theLibrary) {

        File libraryHomePath = theLibrary.getLibraryHomePath();

        /* All its folders are analysed */
        for (File folder : libraryHomePath.listFiles(new ExcludedDirectories())) {
            MediaItem item = scanMediaItem(folder);
            theLibrary.addMediaItems(item);
        }

        logger.debug(theLibrary.getSize() + " in total in " + theLibrary.getName() + " library.");
    }

    /**
     * @param mediaFile The file containing the media, that can be either a file or a folder.
     *
     * @return A MediaItem object representing the scanned media file.
     */
    private MediaItem scanMediaItem(File mediaFile) {

        /* The media item to be returned is created here */
        MediaItem theItem = new MediaItem(mediaFile);

        /* Files are processed just as files */
        if (mediaFile.isFile()) {
            if (FilenameUtils.getExtension(mediaFile.getAbsolutePath()).equalsIgnoreCase("mp4")) {
                theItem.setKind(MEDIA_VIDEO);
            }
            return theItem;
        }

        /*
         * Otherwise, it's a folder, and it may contain a media file.
         * All the files are scanned as media items and added as children of the current media folder.
         */
        theItem.setKind(CONTAINER_FOLDER);
        File[] files = mediaFile.listFiles(new ExcludedDirectories());
        if (files == null){
            return theItem;
        }

        for (File file : files) {
            /* The media item is scanned */
            MediaItem scannedItem = scanMediaItem(file);

            if (scannedItem.getKind().equals(MEDIA_VIDEO)){
                theItem.setKind(MEDIA_FOLDER);
            }

            /* And added to the current item */
            theItem.addMediaItems(Collections.singleton(scannedItem));
            logger.debug("Item found: " + scannedItem.toString());
        }

        return theItem;
    }

    @Override
    public void consolidateLibraryDates(Library library) {
        for (MediaItem mediaItem : library.getMediaItems()) {
            mediaItem.consolidateDates();
        }
    }

    @Override
    public void consolidateToBeSeen(Library library) {
        for (MediaItem mediaItem : library.getMediaItems()) {
            mediaItem.consolidateToBeSeen();
        }
    }
}
