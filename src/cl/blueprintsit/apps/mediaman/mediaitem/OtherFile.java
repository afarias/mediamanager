package cl.blueprintsit.apps.mediaman.mediaitem;

import java.io.File;

/**
 * @author Andrés Farías on 6/5/17.
 */
public class OtherFile extends MediaItem {
    public OtherFile(File aFile) {
        super(aFile);
    }

    @Override
    public boolean isScene() {
        return false;
    }

    @Override
    public String getType() {
        return "Other";
    }
}
