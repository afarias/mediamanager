package cl.blueprintsit.apps.mediaman.mediaitem;

import java.io.File;
import java.util.List;

/**
 * @author Andrés Farías on 6/5/17.
 */
public class SceneFolder extends Scene {

    /* The items contained in this Scene Folder */
    private List<MediaItem> mediaChildren;

    /**
     * The default constructor takes the single file and creating a scene from it.
     *
     * @param mediaFile     The media file from which it is created.
     * @param mediaChildren The other media contained in this folder scene.
     */
    public SceneFolder(File mediaFile, List<MediaItem> mediaChildren) {
        super(mediaFile);

        this.mediaChildren = mediaChildren;
    }

    public List<MediaItem> getMediaChildren() {
        return mediaChildren;
    }
}
