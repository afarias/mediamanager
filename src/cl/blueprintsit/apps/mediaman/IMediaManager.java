package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;

import java.io.File;

/**
 * This is the main interface for the whole application.
 */
public interface IMediaManager {

    /**
     * This method is responsible for performing several tasks on a given library, such as:
     *   - Consolidating dates.
     *   - Consolidating rankings.
     *   - etc.
     *
     * @param library The library on which the tasks are performed.
     */
    void organizeLibrary(MediaItem library);

    /**
     * This method is responsible for consolidating the library dates files and folders.
     *
     * @param library The library whose dates are to be consolidated.
     */
    public void consolidateLibraryDates(MediaItem library);

    /**
     * This method is responsible for modifying the item's name so it's marked to be seen if it has not been rated.
     *
     * @param library The library to be managed with 2C.
     */
    public boolean consolidateToBeSeen(MediaItem library);
}
