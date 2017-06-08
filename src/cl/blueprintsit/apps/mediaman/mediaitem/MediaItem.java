package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.MediaAnalyser;
import cl.blueprintsit.apps.mediaman.Ranking;
import cl.blueprintsit.apps.mediaman.model.Tag;
import cl.blueprintsit.utils.parser.NoDateFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cl.blueprintsit.apps.mediaman.Ranking.NONE;

/**
 * A Media Item represents a media content: a movie, a scene, etc.
 * It can be either a single file, or a folder containing several others media items or media folders.
 *
 * @author Andrés Farías.
 * @see Scene
 */
public abstract class MediaItem {

    private static final Logger logger = LoggerFactory.getLogger(MediaItem.class);

    /* The physical place for where this item is located */
    private File itemFile;

    private Ranking ranking;

    /** The list of media items contained on this media Item */
    private List<MediaItem> mediaItems;
    private List<Tag> tags;

    /**
     * The default constructor that initialize some fields.
     */
    MediaItem(File itemFile) {
        this.itemFile = itemFile;
        this.mediaItems = new ArrayList<>();
        this.ranking = NONE;
        this.tags = new ArrayList<>();
    }

    /**
     * A more complex constructor receiving both the media file and its children media items.
     *
     * @param itemFile   The media file.
     * @param mediaItems The mediaItem's children.
     */
    public MediaItem(File itemFile, List<MediaItem> mediaItems) {
        this(itemFile);

        this.mediaItems.addAll(mediaItems);
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public File getContainerFolder() {
        return itemFile;
    }

    public File getItemFile() {
        return itemFile;
    }

    public List<MediaItem> getChildrenMediaItems() {
        return mediaItems;
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "itemFile=" + itemFile.getName() +
                '}';
    }

    /**
     * This method is responsible for determining if this media item is a scene or not.
     *
     * @return <code>true</code> if this item is scene and <code>false</code> otherwise.
     */
    public abstract boolean isScene();

    /**
     * This method is responsible for returning the concrete type of the media.
     *
     * @return The media kind.
     */
    public abstract String getType();

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * This method is responsible for determining whether the current media item has a given tag.
     *
     * @param tagType The tag type to be searched.
     *
     * @return <code>true</code> if the tag is in the item and <code>false</code> if not.
     */
    public boolean containsTag(String tagType) {

        for (Tag tag : tags) {
            String type = tag.getType();
            if (type != null && type.toUpperCase().equals(tagType.toUpperCase())) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method is responsible for returning the current media item with the given tag type.
     *
     * @param tagType The tag's type.
     *
     * @return The Tag's value.
     */
    public Tag getTag(String tagType) {
        for (Tag tag : tags) {
            String type = tag.getType();
            if (type != null && type.toUpperCase().equals(tagType.toUpperCase())) {
                return tag;
            }
        }

        throw new IllegalArgumentException("There is no such tag");
    }

    public boolean addTag(Tag tag) {
        return this.tags.add(tag);
    }
}
