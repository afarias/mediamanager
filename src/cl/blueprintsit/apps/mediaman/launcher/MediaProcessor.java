package cl.blueprintsit.apps.mediaman.launcher;

import cl.blueprintsit.apps.mediaman.MediaManager;

import javax.swing.*;
import java.io.File;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

/**
 * This class aims to provide a program for searching media in a given folder
 */
public class MediaProcessor extends JPanel {

    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser(new File("/Volumes/slash"));
        jfc.setFileSelectionMode(DIRECTORIES_ONLY);
        jfc.showDialog(new MediaProcessor(), "Directorio");

        File currentDirectory = jfc.getCurrentDirectory();
        MediaManager mediaManager = new MediaManager();
        mediaManager.addLibrary(currentDirectory);
    }
}
