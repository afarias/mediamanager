package cl.blueprintsit.apps.mediaman.analyser;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaContainer;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaFilm;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaSceneFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * This class implements the different sanity operations on the library.
 *
 * @author Andrés Farías on 6/11/17.
 */
public class TagCorrecter implements IMediaVisitor {

    /** The class logger */
    private static final Logger logger = LoggerFactory.getLogger(TagCorrecter.class);

    @Override
    public int visit(MediaContainer mediaContainer) {
        return removeEmptyTagsVisitor(mediaContainer);
    }

    /**
     * This method is responsible for correcting the empty tags from folders.
     * @param media The item whose empty tags are to be removed.
     * @return The number of removed tags.
     */
    private int removeEmptyTagsVisitor(MediaItem media) {
        int counter = removeEmptyTags(media);

        /* Visiting the children */
        for (MediaItem mediaItem : media.getChildrenMediaItems()) {
            counter += mediaItem.visit(this);
        }

        return counter;
    }

    /**
     * This method is responsible for removing empty tags.
     *
     * @param item The container being visited whose tags are to be removed
     *
     * @return the total number of removed tags.
     */
    private int removeEmptyTags(MediaItem item) {

        /* Empty tags are removed */
        int counter = 0;
        if (item.containsTagValue("", false)) {
            counter += item.removeTagsWithValue("");
            logger.info("{} empty tags removed from container {}", counter, item);
        }
        return counter;
    }

    @Override
    public int visit(MediaFilm mediaFilm) {
        return removeEmptyTagsVisitor(mediaFilm);
    }

    @Override
    public int visit(MediaSceneFile sceneFile) {
        return removeEmptyTagsVisitor(sceneFile);
    }

    private int correctDirectoriesAndChildren(MediaContainer mediaContainer, int counter) {
        String previousFileName = "NOTHING";
        for (MediaItem childItem : mediaContainer.getChildrenMediaItems()) {

            File itemFile = childItem.getItemFile();
            String name = itemFile.getName();
            if (name.startsWith(previousFileName)) {
                String theNewName = name.substring(previousFileName.length());
                String newFileName;

                /* Case when this is a file with the same name of the parent... it should go within... */
                String pathname = itemFile.getParentFile() + "/" + previousFileName;
                boolean exists = new File(pathname).exists();
                boolean directory = new File(pathname).isDirectory();
                if (exists && directory) {
                    newFileName = itemFile.getParentFile() + "/" + previousFileName + "/" + theNewName;
                    boolean moved = itemFile.renameTo(new File(newFileName));
                    if (moved) {
                        logger.info("File renamed: {}", itemFile);
                        counter++;
                    }
                }

            } else {
                previousFileName = name;
            }

            counter += childItem.visit(this);
        }
        return counter;
    }


}