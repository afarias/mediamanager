package cl.blueprintsit.apps.mediaman.launcher;

import cl.blueprintsit.apps.mediaman.*;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaFactory;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;

/**
 * This class aims to provide a program for searching media in a given folder
 */
public class MediaProcessor extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(MediaProcessor.class);

    public static void main(String[] args) {
        //JFileChooser jfc = new JFileChooser(new File("/Volumes/Andres HD"));
        //jfc.setFileSelectionMode(DIRECTORIES_ONLY);
        //jfc.showDialog(new MediaProcessor(), "Select a directory");

        //File currentDirectory = jfc.getCurrentDirectory();
        File currentDirectory = new File("/Volumes/Andres HD");

        /* The library is created */
        MediaItem library = new MediaFactory().createMedia(currentDirectory);

        /* Dejar las fechas del FS igual a las del archivo */
        MediaManager manager = new MediaManager();
        manager.consolidateLibraryDates(library);

        /* Modificar el nombre de los items para ver si deben ser vistos ("[2C]") */
        manager.consolidateToBeSeen(library);
    }
}
