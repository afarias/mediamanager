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

    /** The media item list contained in this media item */
    private List<MediaItem> mediaItems;

    public MediaFilm(File itemFile, List<MediaItem> mediaChildren, TagUtils tagUtils) {
        super(itemFile, tagUtils);
        this.mediaItems = new ArrayList<>(mediaChildren);
    }

    /**
     * This method is responsible for adding media items (folders or files) to this item.
     *
     * @param items The items to be added.
     */
    public void addMediaItems(Set<MediaItem> items) {
        this.mediaItems.addAll(items);
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
