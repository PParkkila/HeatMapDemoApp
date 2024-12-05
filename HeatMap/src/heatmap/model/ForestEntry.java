/**
 *
 */
package heatmap.model;

/**
 * @author Patrik Parkkila
 */
public class ForestEntry extends GridEntry {

    public ForestEntry () {
        super( "Forest", "images/forest.png" );
        this.setHeating( 0.0 );
    }
}

// grass - 0
// water - 0
// forest - 0
// road - 7
// building - 10
