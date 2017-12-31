package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.utils.TagUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MediaItemTest {

    private TagUtils tagUtils = new TagUtils("[", "]");

    @Test
    public void testContainsTagValue1() throws Exception {

        MediaItem testFile = createMedia("/temp/testFile [2C].mp3");
        assertTrue(testFile.containsTagValue("2C", false));
    }

    private MediaItem createMedia(String name) {
        File file = new File(name);

        return new MediaFactory(tagUtils).createMedia(file, tagUtils);
    }

    @Test
    public void testContainsTagValue() throws Exception {
        File file = new File("/temp/ace in the hole [2C]");
        boolean newFile = file.createNewFile();
        if (!newFile) {
            return;
        }

        MediaItem testFile = new MediaFactory(tagUtils).createMedia(file, tagUtils);
        assertTrue(testFile.containsTagValue("2C", false));
    }

    @Test
    public void testContainsTagValue2() throws Exception {
        File file = new File("/temp/ace in the hole [] [] [] [] [] [] [] [] [] [] [] [] [] [] [2C]");
        boolean newFile = file.createNewFile();
        if (!newFile) {
            return;
        }

        MediaItem testFile = new MediaFactory(tagUtils).createMedia(file, tagUtils);
        assertTrue(testFile.containsTagValue("", false));
    }

    @Test
    public void testContainsTagValue3() throws Exception {
        File file = new File("/temp/testFile [].mp3");
        boolean newFile = file.createNewFile();
        if (!newFile) {
            return;
        }

        MediaItem testFile = new MediaFactory(tagUtils).createMedia(file, tagUtils);
        assertTrue(testFile.containsTagValue("", false));
    }

    @Test
    public void testHasRanking01() throws Exception {
        MediaItem testFile = createMedia("/temp/testFile [2C].mp3");

        assertFalse(testFile.hasRanking());
    }
}