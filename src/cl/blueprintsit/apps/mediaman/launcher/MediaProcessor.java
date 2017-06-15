package cl.blueprintsit.apps.mediaman.launcher;

import cl.blueprintsit.apps.mediaman.MediaManager;
import cl.blueprintsit.apps.mediaman.analyser.DateConsolidator;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaFactory;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

/**
 * This class aims to provide a program for searching media in a given folder
 */
public class MediaProcessor extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(MediaProcessor.class);

    public static void main(String[] args) {

        /* The library is created */
        MediaItem library = new MediaFactory().createMedia(new File("/Volumes/Andres HD"));

        /* Let's consolidate the dates */
        MediaManager manager = new MediaManager();
        DateConsolidator dateConsolidator = new DateConsolidator();
        dateConsolidator.consolidateDates(library, true);

        /* Modificar el nombre de los items para ver si deben ser vistos ("[2C]") */
        manager.consolidateToBeSeen(library);
        manager.fixTags(library);

    }

    /**
     * This method is responsible for selecting a directory.
     *
     * @return The directory selected by the user.
     */
    private static File selectFile() {
        JFileChooser jfc = new JFileChooser(new File("/Volumes/Andres HD"));
        jfc.setFileSelectionMode(DIRECTORIES_ONLY);
        jfc.showDialog(new MediaProcessor(), "Select a directory");

        return jfc.getCurrentDirectory();
    }
}
