package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.utils.TagUtils;

import java.io.File;
import java.util.List;

/**
 * A media folder represents a folder containing which is considered a single media scene. A media scene has actors,
 * ranking, other media related, and so on. A media scene may be either:
 * <ul>
 * <li>A folder containing one or more video files and other description files, such info or links.</li>
 * <li>A single file, which contains its info mainly in its filename.</li>
 * </ul>
 *
 * @author Andrés Farías on 6/5/17.
 */
public abstract class MediaScene extends MediaItem {

    /**
     * The default constructor takes the single file and creating a scene from it.
     *
     * @param mediaFile The media file from which it is created.
     */
    public MediaScene(File mediaFile, TagUtils tagUtils) {
        super(mediaFile, tagUtils);
    }

    /**
     * The default constructor takes the single file and creating a scene from it.
     *
     * @param mediaFile The media file from which it is created.
     */
    public MediaScene(File mediaFile, List<MediaItem> children, TagUtils tagUtils) {
        super(mediaFile, children, tagUtils);
    }

    @Override
    public boolean isScene() {
        return true;
    }

    @Override
    public String getType() {
        return "Scene";
    }
}
