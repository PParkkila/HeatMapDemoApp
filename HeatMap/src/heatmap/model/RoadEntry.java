/**
 *
 */
package heatmap.model;

/**
 * @author Patrik Parkkila
 */
public class RoadEntry extends GridEntry {
    /**
     * Constructor for creating a road Addition object.
     */
    public RoadEntry () {
        super( "Road", "images/road.png" );
        this.setHeating( 7.0 );
    }

}
