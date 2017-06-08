package cl.blueprintsit.apps.mediaman.mediaitem;

import cl.blueprintsit.apps.mediaman.model.Tag;
import cl.blueprintsit.utils.TagUtils;
import cl.blueprintsit.utils.dates.DateUtils;

import java.util.List;

/**
 * @author Andrés Farías on 6/8/17.
 */
public class TagFactory {

    private String initDelimiter = "[";
    private String endDelimiter = "]";

    public TagFactory(String initDelimiter, String endDelimiter) {
        this.initDelimiter = initDelimiter;
        this.endDelimiter = endDelimiter;
    }

    /**
     * This method is responsible for parsing the tags from the filename and make them real objects.
     *
     * @param mediaItem The media to be analysed.
     */
    public void createTags(MediaItem mediaItem) {

        String name = mediaItem.getItemFile().getName();
        List<Tag> tags = new TagUtils(initDelimiter, endDelimiter).extractTagsFromLine(name);

        /* Now, tags are typed if recognized */
        for (Tag tag : tags) {
            String tagValue = tag.getValue();

            /* Is it a date if... */
            if (DateUtils.determineDateFormat(tagValue) != null){
                tag.setType("date");
                mediaItem.addTag(tag);
            }

        }
    }


}
