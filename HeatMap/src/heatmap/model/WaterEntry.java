/**
 *
 */
package heatmap.model;

/**
 * @author Patrik Parkkila
 */
public class WaterEntry extends GridEntry {

    public WaterEntry () {
        super( "Water", "images/water.png" );
        this.setHeating( 0.0 );
    }
}
