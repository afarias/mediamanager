package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.Ranking;
import cl.blueprintsit.apps.mediaman.model.Tag;
import cl.blueprintsit.utils.TagUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cl.blueprintsit.apps.mediaman.Ranking.NONE;

/**
 * A Media Item represents a media content: a movie, a scene, etc.
 * It can be either a single file, or a folder containing several others media items or media folders.
 *
 * @author Andrés Farías.
 * @see MediaScene
 */
public abstract class MediaItem implements IMediaItem {

    private static final Logger logger = LoggerFactory.getLogger(MediaItem.class);

    /* The physical place for where this item is located */
    private File itemFile;

    private Ranking ranking;

    /** The list of media items contained on this media Item */
    private List<MediaItem> mediaItems;

    /** The list of the item's tags */
    private List<Tag> tags;

    private TagUtils tagUtils;

    /**
     * The default constructor that initialize some fields.
     */
    MediaItem(File itemFile, TagUtils tagUtils) {
        this.itemFile = itemFile;
        this.tagUtils = tagUtils;
        this.mediaItems = new ArrayList<>();
        this.ranking = NONE;
        this.tags = new ArrayList<>();
    }

    /**
     * A more complex constructor receiving both the media file and its children media items.
     *
     * @param itemFile   The media file.
     * @param mediaItems The mediaItem's children.
     * @param tagUtils   The TagUtils to be used to manage tags.
     */
    public MediaItem(File itemFile, List<MediaItem> mediaItems, TagUtils tagUtils) {
        this(itemFile, tagUtils);

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

    /**
     * This method is responsible for adding media items (folders or files) to this item.
     *
     * @param items The items to be added.
     */
    public void addMediaItems(Set<MediaItem> items) {
        this.mediaItems.addAll(items);
    }

    public List<MediaItem> getChildrenMediaItems() {
        return this.mediaItems;
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

    /**
     * This method is responsible for determining if a given tag is present on the item.
     *
     * @param tagValue The tag value to be searched.
     *
     * @return <code>true</code> if the tag is in the item and <code>false</code> otherwise.
     */
    public boolean containsTagValue(String tagValue) {

        for (Tag tag : tags) {
            String value = tag.getValue();
            if (value != null && value.equals(tagValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method is responsible for removing a tag from the Media Item, always keeping an eye on file system
     * consistency.
     *
     * @param tagValue The tag value to be removed.
     *
     * @return The number of tags removed.
     */
    public int removeTagsWithValue(String tagValue) {

        /* If it exists, the tag is retrieved */
        List<Tag> tagsToBeRemoved = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getValue().equals(tagValue)) {
                tagsToBeRemoved.add(tag);
            }
        }

        /* The tags are removed from the item */
        tags.removeAll(tagsToBeRemoved);

        /* Tags are consolidated to the file */
        this.consolidateTags();
        return tagsToBeRemoved.size();
    }

    /**
     * This method is responsible for consolidating the tags with respect to the filename.
     *
     * @return <code>true</code> if the item was consolidated and false otherwise.
     */
    protected boolean consolidateTags() {

        /* The file name is built from zero, starting for retrieve the filename without any tags */
        String finalName = tagUtils.removeTags(this.itemFile.getName()).trim();
        finalName = tagUtils.appendTags(finalName, tags);

        String pathname = itemFile.getParent() + "/" + finalName;
        File dest = new File(pathname);


        boolean moved = this.itemFile.renameTo(dest);
        if (moved) logger.info("Moved!");
        else logger.error("NOT Moved!");
        return moved;
    }
}
