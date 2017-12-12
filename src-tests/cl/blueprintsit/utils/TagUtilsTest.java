package cl.blueprintsit.utils;

import cl.blueprintsit.apps.mediaman.model.Tag;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.*;

public class TagUtilsTest  {

    private TagUtils tagUtils;

    public TagUtilsTest() {
        tagUtils = new TagUtils("[", "]");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNextTag01() throws IllegalArgumentException {
        tagUtils.getNextTag("some text without a tag");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNextTag02() throws IllegalArgumentException {
        tagUtils.getNextTag("some text without a tag [");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNextTag03() throws IllegalArgumentException {
        tagUtils.getNextTag("");
    }

    @Test()
    public void testGetNextTag04() throws IllegalArgumentException {
        String TEXT_ONE_TAG_2C = "[2C]";
        Tag tag = tagUtils.getNextTag(TEXT_ONE_TAG_2C);

        assertNotNull(tag);
        assertEquals(tag.getValue(), "2C");
        assertEquals(TEXT_ONE_TAG_2C, "[2C]");
    }

    @Test()
    public void testGetNextTag05() throws IllegalArgumentException {
        String text = "[2C] [2U]";
        Tag tag = tagUtils.getNextTag(text);

        assertNotNull(tag);
        assertEquals(tag.getValue(), "2C");
        assertEquals(text, "[2C] [2U]");
    }

    @Test()
    public void testExtractTags01() throws IllegalArgumentException {
        List<Tag> tags = tagUtils.extractTags("some text without a tag [");

        assertNotNull(tags);
        assertTrue(tags.isEmpty());
    }

    @Test()
    public void testExtractTags02() throws IllegalArgumentException {
        List<Tag> tags = tagUtils.extractTags("");

        assertNotNull(tags);
        assertTrue(tags.isEmpty());
    }

    @Test()
    public void testExtractTags03() throws IllegalArgumentException {
        List<Tag> tags = tagUtils.extractTags("[2C]");

        assertNotNull(tags);
        assertEquals(tags.size(), 1);
        assertEquals(tags.get(0).getValue(), "2C");
    }

    @Test()
    public void testExtractTags04() throws IllegalArgumentException {
        List<Tag> tags = tagUtils.extractTags("[2C] [2U]");

        assertNotNull(tags);
        assertEquals(tags.size(), 2);
        assertEquals(tags.get(0).getValue(), "2C");
        assertEquals(tags.get(1).getValue(), "2U");
    }

    @Test
    public void testGetTags01() throws Exception {
        List<Tag> tags = tagUtils.getTags("FileName [2C]");

        assertEquals(1, tags.size());
        assertEquals(tags.get(0).getValue(), "2C");
    }

    @Test
    public void testGetTags02() throws Exception {
        List<Tag> tags = tagUtils.extractTags("FileName [2C] [2U]");

        assertEquals(2, tags.size());
        assertEquals(tags.get(0).getValue(), "2C");
        assertEquals(tags.get(1).getValue(), "2U");
    }

    @Test
    public void testGetTags03() throws Exception {
        List<Tag> tags = tagUtils.extractTags("[N-1] [2C] [2C]");

        assertEquals(3, tags.size());
        assertEquals(tags.get(0).getValue(), "N-1");
        assertEquals(tags.get(1).getValue(), "2C");
        assertEquals(tags.get(2).getValue(), "2C");
    }

    @Test
    public void testGetTags04() throws Exception {
        List<Tag> tags = tagUtils.extractTags("None");

        assertNotNull(tags);
        assertEquals(0, tags.size());
    }

    @Test
    public void testGetTags05() throws Exception {
        List<Tag> tags = tagUtils.extractTags("NO");

        assertNotNull(tags);
        assertEquals(0, tags.size());
    }

    @Test
    public void testRemoveTags01() throws Exception {
        List<Tag> tags = tagUtils.extractTags("FileName [2C]");

        assertEquals(1, tags.size());
    }

    @Test
    public void testRemoveTags02() throws Exception {
        List<Tag> tags = tagUtils.extractTags("FileName [2C] [2C]");

        assertEquals(2, tags.size());
    }

    @Test
    public void testRemoveTags03() throws Exception {
        List<Tag> tags = tagUtils.extractTags("[N-1] [2C] [2C]");

        assertEquals(3, tags.size());
    }

    @Test
    public void testRemoveTags04() throws Exception {
        List<Tag> tags = tagUtils.extractTags("None");

        assertEquals(0, tags.size());
    }

    @Test
    public void testRemoveTags05() throws Exception {
        List<Tag> tags = tagUtils.extractTags("NO");

        assertEquals(0, tags.size());
    }

    @Test
    public void testRemoveTagsFromText01() throws Exception {
        String result = tagUtils.removeTags("[2C]");

        assertEquals("", result);
    }

    @Test
    public void testRemoveTagsFromText02() throws Exception {
        String result = tagUtils.removeTags("[2C] [2C]");

        assertEquals(" ", result);
    }

    @Test
    public void testRemoveTagsFromText() throws Exception {
        String result = tagUtils.removeTags("Filename[2C] [2C]");

        assertEquals("Filename ", result);
    }

    @Test
    public void testHasTag01() throws Exception {
        assertFalse(tagUtils.hasTag(""));
    }

    @Test
    public void testHasTag02() throws Exception {
        assertFalse(tagUtils.hasTag("A [)"));
    }

    @Test
    public void testHasTag03() throws Exception {
        assertFalse(tagUtils.hasTag("A ])"));
    }

    @Test
    public void testHasTag05() throws Exception {
        assertTrue(tagUtils.hasTag("A []"));
    }

    @Test
    public void testHasTag06() throws Exception {
        assertTrue(tagUtils.hasTag("A [] []"));
    }

    @Test
    public void testHasTag07() throws Exception {
        assertTrue(tagUtils.hasTag("A [ [] ] []"));
    }

    @Test
    public void testAppendTags() throws Exception {
        Tag tag = new Tag("2C");
        ArrayList<Tag> tags = new ArrayList<>(Arrays.asList(tag));
        String resultingTest = tagUtils.appendTags("filename", tags);

        assertTrue(tagUtils.hasTag(resultingTest));
        TestCase.assertEquals(tag, tagUtils.getNextTag(resultingTest));
    }
}