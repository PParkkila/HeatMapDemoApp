/**
 *
 */
package heatmap.model;

/**
 * @author Patrik Parkkila
 */
public class GrassEntry extends GridEntry {

    public GrassEntry () {
        super( "Grass", "images/grass.png" );
        this.setHeating( 0.0 );
    }
}
