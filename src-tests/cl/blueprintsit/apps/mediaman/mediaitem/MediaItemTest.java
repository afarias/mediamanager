package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.utils.TagUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class MediaItemTest {

    private TagUtils tagUtils = new TagUtils("[", "]");

    @Test
    public void testContainsTagValue1() throws Exception {

        File file = new File("/temp/testFile [2C].mp3");

        MediaItem testFile = new MediaFactory(tagUtils).createMedia(file, tagUtils);
        assertTrue(testFile.containsTagValue("2C"));
    }

    //

    @Test
    public void testContainsTagValue2() throws Exception {
        File file = new File("/temp/ace in the hole [] [] [] [] [] [] [] [] [] [] [] [] [] [] [2C]");
        boolean newFile = file.createNewFile();
        if (!newFile) {
            return;
        }

        MediaItem testFile = new MediaFactory(tagUtils).createMedia(file, tagUtils);
        assertTrue(testFile.containsTagValue(""));
    }

    @Test
    public void testContainsTagValue3() throws Exception {
        File file = new File("/temp/testFile [].mp3");
        boolean newFile = file.createNewFile();
        if (!newFile) {
            return;
        }

        MediaItem testFile = new MediaFactory(tagUtils).createMedia(file, tagUtils);
        assertTrue(testFile.containsTagValue(""));
    }
}