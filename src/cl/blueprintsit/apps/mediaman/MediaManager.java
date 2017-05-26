package cl.blueprintsit.apps.mediaman;

import com.sun.istack.internal.NotNull;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;
import java.security.InvalidParameterException;

public class MediaManager implements IMediaManager {

    @Override
    public void organizeLibrary(Library library) {

        /* The library is iterated through it's media items in order to perform the organization */
        for (MediaItem item: library.getMediaItems()) {

            item.consolidateDates();
        }
    }

    @Override
    public Library addLibrary(@NotNull File directoryLibrary) {


        /* Basic validations */
        if (!directoryLibrary.isDirectory()){
            String errorMessage = "The path " + directoryLibrary.getPath() + " is not valid.";
            System.out.println(errorMessage);
            throw new InvalidParameterException(errorMessage);
        }

        /* The library object is created */
        Library library = new Library(directoryLibrary);
        MediaAnalyser mediaAnalyser = new MediaAnalyser(new MediaItem(directoryLibrary));

        System.out.println("Processing the following path: " + directoryLibrary);

        /* The library is analyzed so all the items are represented with objects */
        mediaAnalyser.refresh();

        return library;
    }

}
