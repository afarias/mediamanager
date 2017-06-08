package cl.blueprintsit.apps.mediaman.model;

/**
 * A Tag is a property of an item.
 * A tag always have a value, but not always necessarily a type.
 *
 * @author Andrés Farías on 6/8/17.
 */
public class Tag {

    /** The Tag's value */
    private String value;

    /** The Tag's type */
    private String type;

    /**
     * The most simple constructor requires a value for the tag.
     *
     * @param value The tag's value.
     */
    public Tag(String value) {
        this.value = value;
    }

    /**
     * A more complete constructor that accept the tag's value and its type.
     *
     * @param value The tag's value.
     * @param type  The tag's type.
     */
    public Tag(String value, String type) {
        this(value);
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type + "{" + value + '}';
    }
}
