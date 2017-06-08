package cl.blueprintsit.utils.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrés Farías on 6/5/17.
 */
public class ListUtils {

    public static List filter(List items, Class type) {

        List result = new ArrayList<>();
        for (Object item : items) {
            if (type.isInstance(item)) {
                result.add(item);
            }
        }
        return result;
    }
}
