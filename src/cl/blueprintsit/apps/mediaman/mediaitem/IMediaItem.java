package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;
import cl.blueprintsit.apps.mediaman.model.Tag;

import java.util.List;

/**
 * This interfaces specifies the general behaviour for media items. In particular, the visitor behaviour.
 *
 * @author Andrés Farías on 6/17/17.
 */
public interface IMediaItem {

    public int visit(IMediaVisitor mediaVisitor);

    public List<MediaItem> getChildrenMediaItems();

    /**
     * This method is responsible for determining if a given tag is present on the item.
     *
     * @param tagValue      The tag value to be searched.
     * @param sensitiveCase Indicates if the comparison will be sensitive case or not.
     *
     * @return <code>true</code> if the tag is in the item and <code>false</code> otherwise.
     */
    boolean containsTagValue(String tagValue, Boolean sensitiveCase);

    /**
     * This method is responsible for determining if a given tag is present on the item.
     *
     * @param aTag      The tag value to be searched.
     * @param sensitiveCase Indicates if the comparison will be sensitive case or not.
     *
     * @return <code>true</code> if the tag is in the item and <code>false</code> otherwise.
     */
    boolean containsTag(Tag aTag, boolean sensitiveCase);

    /**
     * This method is responsible for determining if <code>this</code> media item is to be seen or not.
     *
     * @return <code>true</code> if it has to be seen and <code>false</code> otherwise.
     */
    public boolean isToBeSeen();

    /**
     * This method is responsible for determining if this media item has a Ranking (different than None).
     *
     * @return <code>true</code> if it has a ranking and <code>false</code> otherwise.
     */
    public boolean hasRanking();
}
