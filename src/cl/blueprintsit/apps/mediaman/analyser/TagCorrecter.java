package cl.blueprintsit.apps.mediaman.analyser;

import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Andrés Farías on 6/11/17.
 */
public class TagCorrecter implements ICorrecter {

    private static final Logger logger = LoggerFactory.getLogger(YearCorrecter.class);

    @Override
    public int fix(MediaItem item) {

        String previousFileName = "NOTHING";
        int counter = 0;
        for (MediaItem childItem : item.getChildrenMediaItems()) {

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

            counter += fix(childItem);
        }

        return counter;
    }
}
