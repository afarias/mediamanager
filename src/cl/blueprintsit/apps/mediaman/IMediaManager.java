package cl.blueprintsit.apps.mediaman;

import java.io.File;

/**
 * This is the main interface for the whole application.
 */
public interface IMediaManager {

    void organizeLibrary(Library library);

    /**
     * This method is responsible for adding a folder as a new Library.
     *
     * @param directoryLibrary The folder directory where the directory is to be found.
     *
     * @return A <code>Library</code> object representing the added library.
     */
    public Library addLibrary(File directoryLibrary);
}
