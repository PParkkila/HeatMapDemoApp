/**
 *
 */
package heatmap.model;

/**
 * @author Patrik Parkkila
 */
public class BuildingEntry extends GridEntry {

    /**
     * Constructor for creating a building object. Default name as "Building".
     */
    public BuildingEntry () {
        super( "Building", "images/building.png" );
        this.setHeating( 10.0 );
    }

}
