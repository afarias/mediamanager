package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.utils.TagUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MediaItemTest {

    @Test
    public void testContainsTagValue() throws Exception {
        File file = new File("/temp/testFile [2C].mp3");
        boolean newFile = file.createNewFile();
        if (!newFile){
            return;
        }

        TagUtils tagUtils = new TagUtils("[", "]");
        MediaItem testFile = new MediaFactory(tagUtils).createMedia(file, tagUtils);

        boolean contains = testFile.containsTagValue("2C");

        assertTrue(contains);
    }
}