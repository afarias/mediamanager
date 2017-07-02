package cl.blueprintsit.apps.mediaman.analyser;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaContainer;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaFilm;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import cl.blueprintsit.apps.mediaman.mediaitem.SceneFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Andrés Farías on 6/11/17.
 */
public class TagCorrecter implements IMediaVisitor {

    private static final Logger logger = LoggerFactory.getLogger(YearCorrecter.class);

    @Override
    public int visit(MediaContainer mediaContainer) {

        int counter = 0;
        if (mediaContainer.getItemFile().getName().contains("[]")) {
            mediaContainer.removeTagsWithValue("");
        }

        for (MediaItem mediaItem : mediaContainer.getChildrenMediaItems()) {
            mediaItem.visit(this);
        }

        return counter;
    }

    @Override
    public int visit(MediaFilm mediaFilm) {
        return 0;
    }

    @Override
    public int visit(SceneFile sceneFile) {
        return 0;
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