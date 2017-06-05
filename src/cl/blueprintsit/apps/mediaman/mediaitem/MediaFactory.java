package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.ExcludedDirectories;
import cl.blueprintsit.apps.mediaman.Ranking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static cl.blueprintsit.apps.mediaman.Ranking.*;

/**
 * This class is responsible for handling the logic for creating Libraries, Media Folders and Media Items on the
 * Library.
 *
 * @author Andrés Farías on 6/5/17.
 */
public class MediaFactory {

    private static final Logger logger = LoggerFactory.getLogger(MediaFactory.class);

    /**
     * This method is responsible for creating an object that represents a file system library.
     *
     * @param mediaFile The path of the library's file system.
     *
     * @return An object representing the library.
     */
    public MediaItem createMedia(File mediaFile) {


        /* The library is created from its root folder and its basic data is set */
        MediaItem media;

        /* Cases are analysed: simpler case first: a single file */
        if (mediaFile.isFile()) {
            MediaItem singleItem = createSingleFile(mediaFile);
            logger.info("Item created: " + singleItem);
            return singleItem;
        }

        /* If it's not a single file it's a folder, and a further analysis is in order. */
        List<MediaItem> mediaChildren = new ArrayList<>();
        for (File mediaChild : mediaFile.listFiles(new ExcludedDirectories())) {

            /* The file is processed and a Media Item is obtained from it */
            MediaItem mediaItem = createMedia(mediaChild);

            /* And added to a list for further analysis */
            mediaChildren.add(mediaItem);
        }

        /* In order to determine which kind of media it is, it is necessary to analyse all its contents.
         * So far, the mediaFile can be:
         *  - A Folder Container (General Folder): it contains no video media: no scenes.
         *  - A Film: contains only scenes folders and video (no Folder Container).
         *  - A SceneFolder: contains only scenes.
         */
        if (!contains(mediaChildren, Scene.class)) {
            media = new MediaContainer(mediaFile, mediaChildren);
            logger.info("General Folder created: " + media);
        }

        /* Maybe it's a movie film */
        else if (contains(mediaChildren, MediaContainer.class)) {
            media = new MediaFilm(mediaFile, mediaChildren);
            logger.info("Film created: " + media);
        }

        else {
            media = new SceneFolder(mediaFile, mediaChildren);
            logger.info("Scene created: " + media);
        }

        return media;
    }

    /**
     * This method is responsible for determining if the list of medias contains any specific <code>mediaClass</code>.
     *
     * @param mediaChildren The list of media to be tested.
     * @param mediaClass    The kind of media.
     *
     * @return <code>true</code> if it contains at least one item of the specified media type class and
     * <code>false</code> otherwise.
     */
    private boolean contains(List<MediaItem> mediaChildren, Class mediaClass) {

        for (MediaItem mediaChild : mediaChildren) {

            /* If any child is a scene ... */
            if (mediaClass.isInstance(mediaChild)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method is responsible for creating the right instance of a file. A file can be ether a media type, and then
     * it is created as a SceneFile. Otherwise, as a general File.
     *
     * @param mediaFile The File to be created.
     *
     * @return An instance of a File.
     */
    private MediaItem createSingleFile(File mediaFile) {

        /* The file might be a video media file in which case is a scene file: */
        if (isVideo(mediaFile)) {
            return new SceneFile(mediaFile);
        } else {
            return new OtherFile(mediaFile);
        }
    }

    /**
     * This method is responsible for determining if a given file is a video or not.
     *
     * @param mediaFile The file to be analysed.
     *
     * @return <code>true</code> if the file is of type VIDEO MIME and <code>false</code> otherwise.
     */
    private boolean isVideo(File mediaFile) {

        Path path = Paths.get(mediaFile.toURI());
        try {
            String contentType = Files.probeContentType(path);
            logger.debug("MimeType of " + mediaFile.getName() + ": " + contentType);

            return contentType.startsWith("video");
        } catch (IOException e) {
            logger.error("Exception while reading the file '" + mediaFile + "'.", e);
            return false;
        }
    }

    /**
     * This method is responsible for initializing the Ranking media item.
     */
    public static Ranking parseRanking(MediaItem mediaItem) {
        File itemFile = mediaItem.getItemFile();
        if (itemFile.getName().contains("[N-10]")) {
            return TEN;
        } else if (itemFile.getName().contains("[N-9]")) {
            return NINE;
        } else if (itemFile.getName().contains("[N-8]")) {
            return EIGHT;
        } else if (itemFile.getName().contains("[N-7]")) {
            return SEVEN;
        } else if (itemFile.getName().contains("[N-6]")) {
            return SIX;
        } else return NONE;
    }
}
