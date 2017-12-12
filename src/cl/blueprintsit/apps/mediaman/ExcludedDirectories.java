package cl.blueprintsit.apps.mediaman;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Andrés Farías on 5/30/17.
 */
public class ExcludedDirectories implements FileFilter {
    @Override
    public boolean accept(File f) {

        String extension = FilenameUtils.getExtension(f.getAbsolutePath());
        return !(extension.contains("DocumentRevisions") ||
                extension.contains("DS_Store") ||
                extension.contains("Spotlight-") ||
                extension.contains("TemporaryItems") ||
                extension.contains("Trashes") ||
                extension.contains("fseventsd") ||
                extension.contains("savedSearch") ||
                f.getName().equalsIgnoreCase("temp") ||
                f.getName().equalsIgnoreCase("VolumeIcon.icns") ||
                f.getName().startsWith("_"));

    }
}
