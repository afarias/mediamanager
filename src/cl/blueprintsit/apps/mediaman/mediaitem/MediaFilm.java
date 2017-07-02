package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.utils.TagUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Andrés Farías on 6/5/17.
 */
public class MediaFilm extends MediaItem {

    public MediaFilm(File itemFile, List<MediaItem> mediaChildren, TagUtils tagUtils) {
        super(itemFile, mediaChildren, tagUtils);
    }

    @Override
    public boolean isScene() {
        return false;
    }

    @Override
    public String getType() {
        return "Film";
    }

    @Override
    public int visit(IMediaVisitor mediaVisitor) {
        return mediaVisitor.visit(this);
    }
}
