package cl.blueprintsit.apps.mediaman.analyser;

import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import cl.blueprintsit.apps.mediaman.mediaitem.SceneFile;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class YearCorrecterTest {

    @Test
    public void testRepairParenthesis() throws Exception {
        MediaItem mediaItem = new SceneFile(new File("/Volumes/Andres HD/temp/Big Tits get cum on them - (Bridgette B) (2012)"));
        int i = new YearCorrecter().repairYearInParenthesis(mediaItem);

        assertEquals(1, i);
    }
}