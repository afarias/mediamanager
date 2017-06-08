package cl.blueprintsit.apps.mediaman.mediaitem;

import java.io.File;
import java.util.List;

/**
 * This class represents a folder containing other folders, and not a specific media.
 *
 * @author Andrés Farías on 6/5/17.
 */
public class MediaContainer extends MediaItem {

    public MediaContainer(File mediaFile, List<MediaItem> mediaChildren) {
        super(mediaFile, mediaChildren);
    }

    @Override
    public boolean isScene() {
        return false;
    }

    @Override
    public String getType() {
        return "Container";
    }
}
