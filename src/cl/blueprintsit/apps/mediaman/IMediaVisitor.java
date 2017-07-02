package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.apps.mediaman.mediaitem.MediaContainer;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaFilm;
import cl.blueprintsit.apps.mediaman.mediaitem.MediaSceneFile;

/**
 * @author Andrés Farías on 6/17/17.
 */
public interface IMediaVisitor {

    public int visit(MediaContainer mediaContainer);
    public int visit(MediaFilm mediaFilm);
    public int visit(MediaSceneFile sceneFile);
}
