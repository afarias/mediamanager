package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.utils.TagUtils;

import java.io.File;
import java.util.List;

/**
 * This class represents a folder containing other folders, and not a specific media.
 *
 * @author Andrés Farías on 6/5/17.
 */
public class MediaContainer extends MediaItem {

    public MediaContainer(File mediaFile, List<MediaItem> mediaChildren, TagUtils tagUtils) {
        super(mediaFile, mediaChildren, tagUtils);
    }

    @Override
    public boolean isScene() {
        return false;
    }

    @Override
    public String getType() {
        return "Container";
    }

    @Override
    public int visit(IMediaVisitor mediaVisitor) {
        return mediaVisitor.visit(this);
    }
}
