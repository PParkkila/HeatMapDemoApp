/**
 *
 */
package heatmap.util;

import org.junit.Before;
import org.junit.Test;

import heatmap.model.GridEntry;

/**
 *
 */
public class LinkedGridTest {

    // @Test
    // public void test () {
    // fail( "Not yet implemented" );
    // }

    private LinkedGrid grid;

    @Before
    public void setup () {
        grid = new LinkedGrid();
    }

    @Test
    public void testPrintGridNames () {

        // grid.printGridNames();
    }

    @Test
    public void testGetNode () {
        GridEntry road = new GridEntry( "Road", null );
        grid.updateElement( 1, 1, road );

        System.out.println( ( grid.getElement( 1, 1 ) ).getName() );

        // grid.printGridNames();

    }

    @Test
    public void testAddingDimensions () {

        // grid.addColumnRight();
        //
        // assertEquals( 2, grid.width() );
        // assertEquals( 2, grid.size() );
        // assertEquals( 1, grid.height() );
        //
        // // grid.printGridNames();
        //
        // grid.addRowBottom();
        //
        // assertEquals( 2, grid.width() );
        // assertEquals( 4, grid.size() );
        // assertEquals( 2, grid.height() );
        //
        // grid.printGridNames();
        // System.out.println( "\n\n" );
        //
        // grid.addRowBottom();
        // grid.addRowBottom();
        // grid.addRowBottom();
        // grid.addRowBottom();
        //
        // grid.printGridNames();
        // System.out.println( "\n\n" );
        //
        // grid.addColumnRight();
        // grid.addColumnRight();
        // grid.addColumnRight();
        //
        // grid.printGridNames();
        // System.out.println( "\n\n" );

        for ( int i = 0; i < 9; i++ ) {
            grid.addColumnRight();
            grid.addRowBottom();
        }

        grid.printGridNames();

    }

}
