package cl.blueprintsit.apps.mediaman.launcher;

import cl.blueprintsit.apps.mediaman.IMediaManager;
import cl.blueprintsit.apps.mediaman.Library;
import cl.blueprintsit.apps.mediaman.MediaManager;
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
        //JFileChooser jfc = new JFileChooser(new File("/Volumes/Andres HD"));
        //jfc.setFileSelectionMode(DIRECTORIES_ONLY);
        //jfc.showDialog(new MediaProcessor(), "Directorio");

        //File currentDirectory = jfc.getCurrentDirectory();
        File currentDirectory = new File("/Volumes/Andres HD");
        IMediaManager mediaManager = new MediaManager();
        Library library = mediaManager.createLibrary(currentDirectory);
        logger.info("The folder " + currentDirectory.getAbsolutePath() + " has been created as library " + library.getName());

        mediaManager.consolidateLibraryDates(library);
    }
}
