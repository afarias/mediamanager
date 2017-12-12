package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.ExcludedDirectories;
import cl.blueprintsit.apps.mediaman.Ranking;
import cl.blueprintsit.utils.TagUtils;
import cl.blueprintsit.utils.strings.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static cl.blueprintsit.apps.mediaman.Ranking.*;
import static cl.blueprintsit.utils.lists.ListUtils.filter;

/**
 * This class is responsible for handling the logic for creating Libraries, Media Folders and Media Items on the
 * Library.
 *
 * @author Andrés Farías on 6/5/17.
 */
public class MediaFactory {

    private static final Logger logger = LoggerFactory.getLogger(MediaFactory.class);

    TagFactory tagFactory;

    public MediaFactory(TagUtils tagUtils) {
        tagFactory = new TagFactory(tagUtils.getInitDelimiter(), tagUtils.getEndDelimiter());
    }

    /**
     * This method is responsible for creating an object that represents a file system library of an specific type.
     * This are the cases to be taken into account:
     * <ul>
     *     <li>No media files</li>
     *     <li>Single media files</li>
     *     <li>Scene folder: 1 media file & zero or more no video-files.</li>
     *     <li>Movie folder: 1 media file & zero or more no video-files.</li>
     *     <li>Container folder: nothing like before...</li>
     * </ul>
     * @param mediaFile The path of the library's file system.
     *
     * @return An object representing the library.
     */
    public MediaItem createMedia(File mediaFile, TagUtils tagUtils) {

        MediaItem media;
        List<MediaItem> mediaChildren;

        /* Cases are analysed: simpler case first: a single file */
        if (mediaFile.isFile()) {
            media = createSingleFile(mediaFile, tagUtils);
            tagFactory.createTags(media);
        }

        /* If it's not a single file it's a folder, and a further analysis is in order. */
        else {

            /* First is to create (recursively) the children and then analyze them */

            mediaChildren = new ArrayList<>();
            File[] fileChildren = mediaFile.listFiles(new ExcludedDirectories());
            for (File mediaChild : fileChildren) {

                /* The file is processed and a Media Item is obtained from it */
                MediaItem mediaItem = createMedia(mediaChild, tagUtils);

                /* And added to a list for further analysis */
                mediaChildren.add(mediaItem);
            }

            /* In order to determine which kind of media it is, it is necessary to analyse all its contents.
             * So far, the mediaFile can be:
             *  - A Folder Media Container (General Folder): it contains only scenes and nothing else!
             *  - A Film: contains only scenes folders and video (no Folder Container).
             *  - A SceneFolder: contains only scenes.
             */

            /* First it's tested for being a scene */
            if (isScene(mediaFile, mediaChildren)) {
                media = new MediaSceneFolder(mediaFile, mediaChildren, tagUtils);
            } else if (isFilm(mediaFile, mediaChildren)) {
                media = new MediaFilm(mediaFile, mediaChildren, tagUtils);
            } else {
                media = new MediaContainer(mediaFile, mediaChildren, tagUtils);
            }

            logger.debug("Media item created: " + media + " of type " + media.getType());
        }

        tagFactory.createTags(media);
        return media;
    }

    /**
     * This method is responsible for determining if the given mediaFile and its children corresponds to a scene:
     * <ul><li>If there is only one child, and it's a scene file</li></ul>
     *
     * @param mediaFile     The media file to be tested.
     * @param mediaChildren The media file children.
     *
     * @return <code>true</code> if the media items match de Film definition and <code>false</code> otherwise.
     */
    private boolean isScene(File mediaFile, List<MediaItem> mediaChildren) {
        List scenes = filter(mediaChildren, MediaSceneFile.class);
        return scenes.size() == 1;
    }

    /**
     * This method is responsible for determining if the given mediaFile and its children corresponds to a film:
     * <ul><li>If the children corresponds to more than one Scene File!</li></ul>
     *
     * @param mediaFile     The media file to be tested.
     * @param mediaChildren The media file children.
     *
     * @return <code>true</code> if the media items match de Film definition and <code>false</code> otherwise.
     */
    private boolean isFilm(File mediaFile, List<MediaItem> mediaChildren) {

        if (!mediaFile.isDirectory()) {
            return false;
        }

        boolean containsContainers = contains(mediaChildren, MediaContainer.class);
        boolean containsFilms = contains(mediaChildren, MediaFilm.class);
        if (containsContainers || containsFilms) {
            return false;
        }

        /* If the folder only contains scenes (and no films or other files, we took it as a something else */
        if (containsOnly(mediaChildren, MediaScene.class)) {
            return false;
        }

        return true;
    }

    private boolean containsOnlyScenes(List<MediaItem> mediaChildren) {
        return !containsOnly(mediaChildren, MediaScene.class);
    }

    /**
     * This method is responsible for determining if the list of medias is only composed by the given type of media.
     *
     * @param mediaItems The media to be tested.
     * @param mediaClass The kind of media to be tested.
     *
     * @return <code>true</code> if every media is of the given <code>mediaClass</code> and <code>false</code> if there
     * is at least one media item that is not of the given type.
     */
    private boolean containsOnly(List<MediaItem> mediaItems, Class... mediaClass) {

        for (MediaItem mediaItem : mediaItems) {
            if (!containsAny(mediaItem, mediaClass)) {
                return false;
            }
        }

        return true;
    }

    private boolean containsAny(MediaItem mediaItem, Class[] mediaClasses) {
        for (Class mediaClass : mediaClasses) {
            if (mediaClass.isInstance(mediaItem)) {
                return true;
            }

        }

        return false;
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
                return true;
            }
        }

        return false;
    }

    /**
     * This method is responsible for creating the right instance of a file. A file can be ether a media type, and then
     * it is created as a SceneFile. Otherwise, as a general File.
     *
     * @param mediaFile The File to be created.
     *
     * @return An instance of a File.
     */
    private MediaItem createSingleFile(File mediaFile, TagUtils tagUtils) {

        /* The file might be a video media file in which case is a scene file: */
        if (isVideo(mediaFile)) {

            /* The business object is created */
            MediaSceneFile sceneFile = new MediaSceneFile(mediaFile, tagUtils);
            return sceneFile;
        } else {
            return new OtherFile(mediaFile, tagUtils);
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
        String contentType = FilenameUtils.getExtension(mediaFile.getAbsolutePath());
        logger.debug("Extension of " + mediaFile.getName() + ": " + contentType);

        boolean doesContains = StringUtils.containsAny(contentType, "MPG", "MPEG", "MP4", "MKV", "WMV", "AVI", "MKA", "MOV");
        if (!doesContains && !StringUtils.containsAny(contentType, "", "crdownload", "RAR", "RTF", "SRT", "AC3", "INI", "NFO", "TXT", "WEBLOC", "JPEG", "JPG", "SAVEDSEARCH", "URL", "PUP", "ASS", "DB")) {
            logger.error("NOT RECOGNIZED EXTENSION!!!");
        }
        return doesContains;
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
