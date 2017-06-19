package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.utils.TagUtils;

import java.io.File;

/**
 * @author Andrés Farías on 6/5/17.
 */
public class SceneFile extends Scene {

    /**
     * The default constructor takes the single file and creating a scene from it.
     *
     * @param mediaFile The media file from which it is created.
     */
    public SceneFile(File mediaFile, TagUtils tagUtils) {
        super(mediaFile, tagUtils);
    }

    @Override
    public int visit(IMediaVisitor mediaVisitor) {
        return mediaVisitor.visit(this);
    }
}
