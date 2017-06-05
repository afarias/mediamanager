package cl.blueprintsit.apps.mediaman;

import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import cl.blueprintsit.utils.parser.NoDateFoundException;
import cl.blueprintsit.utils.parser.StringParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

public class MediaAnalyser {

    private static final Logger logger = LoggerFactory.getLogger(MediaAnalyser.class);

    /* The item to be analyzed */
    private MediaItem mediaItem;

    public MediaAnalyser(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public MediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    /**
     * This method is responsible for parsing the release date from the folder name.
     *
     * @return The date object that represents the release date.
     */
    public Date getReleaseDate() throws NoDateFoundException {
        File containerFolder = mediaItem.getContainerFolder();
        return StringParser.extractDate(containerFolder.getName());
    }
}
