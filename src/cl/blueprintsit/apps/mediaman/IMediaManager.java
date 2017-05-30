package cl.blueprintsit.apps.mediaman;

import java.io.File;

/**
 * This is the main interface for the whole application.
 */
public interface IMediaManager {

    void organizeLibrary(Library library);

    /**
     * This method is responsible for creating a new Library based on a FileSystem folder.
     *
     * @param directoryLibrary The folder directory where the directory is to be found.
     *
     * @return A <code>Library</code> object representing the processed library.
     */
    public Library createLibrary(File directoryLibrary);

    /**
     * This method is responsible for consolidating the library dates files and folders.
     *
     * @param library The library whose dates are to be consolidated.
     */
    public void consolidateLibraryDates(Library library);
}
