package cl.blueprintsit.apps.mediaman.analyser;

import cl.blueprintsit.apps.mediaman.mediaitem.MediaItem;
import cl.blueprintsit.apps.mediaman.model.Tag;
import cl.blueprintsit.utils.dates.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.*;

/**
 * This class is responsible for defining the algorithms for consolidating dates.
 *
 * @author Andrés Farías on 6/8/17.
 */
public class DateConsolidator {

    private static final Logger logger = LoggerFactory.getLogger(MediaItem.class);

    /**
     * Consolidating a container folder might should only be performed if the folder itself have dates.
     * Otherwise, the consolidation is performed only on their children.
     *
     * @return A list of the items whose date was modified.
     */
    public List<MediaItem> consolidateDates(MediaItem mediaItem, boolean recursive) {

        List<MediaItem> modifiedItems = new ArrayList<>();

        /* The date time is obtained. If there is not, there is nothing to do */
        if (mediaItem.containsTag("date")) {
            Tag dateTag = mediaItem.getTag("date");
            logger.debug("DATE VALUE = {}", dateTag);
            Date dateItem = DateUtils.format(dateTag.getValue());

            /* Checked if the consolidation is necessary */
            File itemFile = mediaItem.getItemFile();
            Date fileDate = new Date(itemFile.lastModified());

            if (!almostEquals(fileDate, dateItem)) {
                boolean modified = itemFile.setLastModified(dateItem.getTime());
                logger.info("Item " + mediaItem.toString() + ": Last modified date set to " + dateItem + "(" + modified + ")");

                if (modified) {
                    modifiedItems.add(mediaItem);
                }
            }
        }

        /* This is the recursive call (if required) */
        if (recursive) {
            for (MediaItem childItem : mediaItem.getChildrenMediaItems()) {
                modifiedItems.addAll(consolidateDates(childItem, true));
            }
        }

        return modifiedItems;
    }

    /**
     * This method is responsible for comparing two dates and determining if they are "equals" up to the day. Two dates
     * with different hours, but same year, month and day, are then considered equals.
     *
     * @param date1 The first date to be compared.
     * @param date2 The second date to be compared.
     *
     * @return <code>true</code> if they are almost equals, and <code>false</code> if they are not.
     */
    private boolean almostEquals(Date date1, Date date2) {

        if (date1 == null || date2 == null){
            throw new IllegalArgumentException("The dates are not to be null: " + date1 + "/" + date2);
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean yearComp = cal1.get(YEAR) == cal2.get(YEAR);
        boolean monthComp = cal1.get(MONTH) == cal2.get(MONTH);
        boolean dayComp = cal1.get(DAY_OF_MONTH) == cal2.get(DAY_OF_MONTH);

        return yearComp && monthComp && dayComp;
    }

}
