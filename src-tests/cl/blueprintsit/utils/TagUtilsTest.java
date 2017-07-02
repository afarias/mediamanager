package cl.blueprintsit.utils;

import cl.blueprintsit.apps.mediaman.model.Tag;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class TagUtilsTest extends TestCase {

    private TagUtils tagUtils;

    public TagUtilsTest() {
        tagUtils = new TagUtils("[", "]");
    }

    public void testRemoveTags01() throws Exception {
        List<Tag> tags = tagUtils.extractTagsFromLine("FileName [2C]");

        assertEquals(1, tags.size());
    }

    public void testRemoveTags02() throws Exception {
        List<Tag> tags = tagUtils.extractTagsFromLine("FileName [2C] [2C]");

        assertEquals(2, tags.size());
    }

    public void testRemoveTags03() throws Exception {
        List<Tag> tags = tagUtils.extractTagsFromLine("[N-1] [2C] [2C]");

        assertEquals(3, tags.size());
    }

    public void testRemoveTags04() throws Exception {
        List<Tag> tags = tagUtils.extractTagsFromLine("None");

        assertEquals(0, tags.size());
    }

    public void testRemoveTags05() throws Exception {
        List<Tag> tags = tagUtils.extractTagsFromLine("NO");

        assertEquals(0, tags.size());
    }

    public void testRemoveTagsFromText01() throws Exception {
        String result = tagUtils.removeTagsFromText("[2C]");

        assertEquals("", result);
    }

    public void testRemoveTagsFromText02() throws Exception {
        String result = tagUtils.removeTagsFromText("[2C] [2C]");

        assertEquals(" ", result);
    }

    public void testRemoveTagsFromText() throws Exception {
        String result = tagUtils.removeTagsFromText("Filename[2C] [2C]");

        assertEquals("Filename ", result);
    }
}