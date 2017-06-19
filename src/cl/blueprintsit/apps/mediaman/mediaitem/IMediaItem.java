package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.IMediaVisitor;

import java.util.List;

/**
 * This interfaces specifies the general behaviour for media items. In particular, the visitor behaviour.
 *
 * @author Andrés Farías on 6/17/17.
 */
public interface IMediaItem {

    public int visit(IMediaVisitor mediaVisitor);

    public List<MediaItem> getChildrenMediaItems();
}
