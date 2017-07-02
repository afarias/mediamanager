package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.utils.TagUtils;

import java.io.File;
import java.util.List;

/**
 * @author Andrés Farías on 6/5/17.
 */
public class MediaSceneFolder extends MediaScene {

    /**
     * The default constructor takes the single file and creating a scene from it.
     *
     * @param mediaFile     The media file from which it is created.
     * @param mediaChildren The other media contained in this folder scene.
     */
    public MediaSceneFolder(File mediaFile, List<MediaItem> mediaChildren, TagUtils tagUtils) {
        super(mediaFile, mediaChildren, tagUtils);
    }

    public MediaSceneFolder(File file, TagUtils tagUtils) {
        super(file, tagUtils);
    }

    @Override
    public int visit(IMediaVisitor mediaVisitor) {
        return 0;
    }
}
