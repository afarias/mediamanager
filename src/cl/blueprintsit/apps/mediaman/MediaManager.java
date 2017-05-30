package cl.blueprintsit.apps.mediaman;

import com.sun.deploy.util.ArrayUtil;
import com.sun.istack.internal.NotNull;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaManager implements IMediaManager {

    private static final Logger logger = LoggerFactory.getLogger(MediaManager.class);

    @Override
    public void organizeLibrary(Library library) {

        /* The library is iterated through it's media items in order to perform the organization */
        for (MediaItem item: library.getMediaItems()) {

            item.consolidateDates();
        }
    }

    @Override
    public Library createLibrary(@NotNull File directoryLibrary) {

        /* Basic validations */
        if (!directoryLibrary.isDirectory()){
            String errorMessage = "The path " + directoryLibrary.getPath() + " is not valid.";
            logger.error(errorMessage);
            throw new InvalidParameterException(errorMessage);
        }

        /* The library object is created */
        return new Library(directoryLibrary);
    }

    @Override
    public void consolidateLibraryDates(Library library) {

        for (MediaItem mediaItem : library.getMediaItems()) {
            mediaItem.consolidateDates();
        }
    }
}
