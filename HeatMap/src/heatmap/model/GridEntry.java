/**
 *
 */
package heatmap.model;

/**
 * Defines the entry stored within each grid object
 * @author Patrik Parkkila
 */
public class GridEntry {

    private final String name;

    private final String filePath;

    private double       heating;

    // /**
    // * Generic constructor for a newly added Object. Calls other constructor.
    // */
    // public Addition () {
    // this.name
    // }

    /**
     * Constructor for creating a new object to be added to the model.
     *
     * @param name
     *            the name of the new object
     */
    public GridEntry ( final String name, final String filePath ) {
        this.name = name;
        this.filePath = filePath;
    }

    public String getName () {
        return this.name;
    }

    public String getFilePath () {
        return this.filePath;
    }

    public double getHeating () {
        return this.heating;
    }

    public void setHeating ( double val ) {
        this.heating = val;
    }

}
