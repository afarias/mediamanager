package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.utils.TagUtils;

import java.io.File;

/**
 * @author Andrés Farías on 6/5/17.
 */
public class OtherFile extends MediaItem {

    public OtherFile(File aFile, TagUtils tagUtils) {
        super(aFile, tagUtils);
    }

    @Override
    public boolean isScene() {
        return false;
    }

    @Override
    public String getType() {
        return "Other";
    }

    @Override
    public int visit(IMediaVisitor mediaVisitor) {
        return 0;
    }
}
