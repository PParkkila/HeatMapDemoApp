/**
 *
 */
package heatmap.util;

import java.io.PrintStream;
import java.util.Random;

import heatmap.model.ForestEntry;
import heatmap.model.GrassEntry;
import heatmap.model.GridEntry;
import heatmap.model.WaterEntry;

/**
 * @author Patrik Parkkila
 */
public class LinkedGrid {

    /** The current amount of elements in the entire linked grid. */
    private int                 size;

    /** The current amount of elements across horizontally for the grid. */
    private int                 width;

    /** The current amount of elements across vertically for the grid. */
    private int                 height;

    /** The Node in the top left of the grid. */
    private LinkedGridNode      topLeft;

    private static final double CALCULATION_MULTIPLIER = 0.313;

    public LinkedGrid () {
        // TODO constructor for the linked grid
        topLeft = new LinkedGridNode();

        this.size = 1;
        this.width = 1;
        this.height = 1;

    }

    public LinkedGridNode getNode ( int row, int column ) {

        // TODO error checks

        LinkedGridNode foundNode = topLeft;

        for ( int i = 1; i < row; i++ ) {
            foundNode = foundNode.getBelow();
        }

        for ( int i = 1; i < column; i++ ) {
            foundNode = foundNode.getRight();
        }

        return foundNode;

    }

    public GridEntry updateElement ( int row, int column, GridEntry elementNew ) {

        LinkedGridNode foundNode = getNode( row, column );

        GridEntry retVal = foundNode.changeElement( elementNew );

        return retVal;

    }

    /** Add a row and column of nodes to the grid. */
    public void addColumnRight () {

        // need to check current dimensions of grid

        // need to iterate to right-most entry in top grid and add a node to the
        // right of it

        LinkedGridNode iterRow = topLeft;

        while ( iterRow.getRight() != null ) {
            iterRow = iterRow.getRight();
        }

        // now that the right-most node is selected
        // create a new node to the right of it - everything null
        // link that new node to the old node (right of current)

        LinkedGridNode newNodeTop = new LinkedGridNode();
        this.size++;
        newNodeTop.setLeft( iterRow ); // assign the reference to the left
        iterRow.setRight( newNodeTop ); // assign the right reference to the new
                                        // node on the right of iterNode
        // everything else for the new node is null

        // create a loop to go through the new column downwards until we hit the
        // size of the row

        LinkedGridNode iterColumn = newNodeTop;
        // start loop at 1 to account for the first item already being created
        for ( int i = 1; i < this.height; i++ ) {
            LinkedGridNode newColNode = new LinkedGridNode();
            this.size++;
            // assign references
            newColNode.setAbove( iterColumn );
            iterColumn.setBelow( newColNode );

            // we want to assign the right of below of the left of the
            // iterated column

            LinkedGridNode iterColumnLeft = ( ( iterColumn.getLeft() ).getBelow() );

            newColNode.setLeft( iterColumnLeft );
            iterColumnLeft.setRight( newColNode );

            // now

            // update the itercolumn

            iterColumn = newColNode;
        }
        this.width++;

        // increment dimensions of grid afterwards
        // and increment size each time a node is added

    }

    /** Method to add a row of grid node objects at the bottom of the grid. */
    public void addRowBottom () {

        LinkedGridNode bottomNode = topLeft;
        this.size++;
        // loop through nodes to the bottom left most node

        while ( bottomNode.getBelow() != null ) {
            bottomNode = bottomNode.getBelow();
        }

        // now we are at the bottom left of the original grid
        // need to add a node under this one
        LinkedGridNode newNodeLeft = new LinkedGridNode();

        // assign the above for new node to be bottom node
        // assign the below for bottom node to be new node
        newNodeLeft.setAbove( bottomNode );
        bottomNode.setBelow( newNodeLeft );

        // now we can start to iterate through and adding nodes to the bottom
        // row similarly to how the addColumnRight adds nodes down the column.

        LinkedGridNode iterRow = newNodeLeft;
        // account for the one element that was already added to the new row
        for ( int i = 1; i < this.width; i++ ) {
            // add a new node
            LinkedGridNode newRowNode = new LinkedGridNode();
            this.size++;
            // link the new node as follows
            newRowNode.setLeft( iterRow );
            iterRow.setRight( newRowNode );

            // need to link from above by corresponding linkednode
            LinkedGridNode aboveNewNode = ( iterRow.getAbove() ).getRight();
            newRowNode.setAbove( aboveNewNode );
            aboveNewNode.setBelow( newRowNode );

            // update the iterRow to be the new row node created
            iterRow = newRowNode;

        }
        // increment height after adding new row
        this.height++;

    }

    public void printGridNames () {

        // need to iterate through row by row
        PrintStream printStream = System.out;

        LinkedGridNode rowLeft = topLeft;
        LinkedGridNode printingNode = topLeft;
        for ( int i = 0; i < this.height; i++ ) {

            printingNode = rowLeft; // assign before entering loop for elements
                                    // in the row
            printStream.printf( " | " );

            for ( int j = 0; j < this.width; j++ ) {

                printStream.printf( "%-10s | ", printingNode.getElement().getName() );

                printingNode = printingNode.getRight();

            }

            rowLeft = rowLeft.getBelow();

            printStream.printf( "\n" );
        }

    }

    public GridEntry getElement ( int row, int column ) {
        // TODO cases where not found
        LinkedGridNode foundNode = getNode( row, column );
        return foundNode.getElement();
    }

    // public int calculateImpact(int row, int column) {
    //
    //
    // }

    public int width () {
        return this.width;
    }

    public int height () {
        return this.height;
    }

    public int size () {
        return this.size;
    }

    /**
     * Assists in calculating the effect that adjacent cells of the table have
     * on the current cell.
     */
    public double calculateAdjacents () {

        double[][] oldEntries = new double[height][width];
        for ( int i = 0; i < height; i++ ) {
            for ( int j = 0; j < width; j++ ) {
                oldEntries[i][j] = getNode( i + 1, j + 1 ).getElement().getHeating();
            }
        }

        for ( int i = 0; i < height; i++ ) {
            for ( int j = 0; j < width; j++ ) {

                // need to take each element and see if there are neighbors in
                // any direction.. if there are, update accordingly. Update by
                // 15 percent of the adjacent.

                // need to account for the original values of the grids

                LinkedGridNode foundNode = getNode( i + 1, j + 1 );

                if ( foundNode.getAbove() != null ) {
                    foundNode.getElement().setHeating(
                            foundNode.getElement().getHeating() + ( oldEntries[i - 1][j] * CALCULATION_MULTIPLIER ) );
                }

                if ( foundNode.getBelow() != null ) {
                    foundNode.getElement().setHeating(
                            foundNode.getElement().getHeating() + ( oldEntries[i + 1][j] * CALCULATION_MULTIPLIER ) );
                }

                if ( foundNode.getRight() != null ) {
                    foundNode.getElement().setHeating(
                            foundNode.getElement().getHeating() + ( oldEntries[i][j + 1] * CALCULATION_MULTIPLIER ) );
                }

                if ( foundNode.getLeft() != null ) {
                    foundNode.getElement().setHeating(
                            foundNode.getElement().getHeating() + ( oldEntries[i][j - 1] * CALCULATION_MULTIPLIER ) );
                }

            }
        }

        double sum = calculateTotalSum();

        // need to reset heating to original after calculating
        for ( int i = 0; i < height; i++ ) {
            for ( int j = 0; j < width; j++ ) {
                getNode( i + 1, j + 1 ).getElement().setHeating( oldEntries[i][j] );
            }
        }

        return sum;
    }

    /**
     * Calculate the total heat for the map. Assumes 10x10 grid.
     *
     * @return
     */
    private double calculateTotalSum () {

        // need a grid

        double sum = 0;

        for ( int i = 0; i < 10; i++ ) {
            for ( int j = 0; j < 10; j++ ) {
                // controller.calculateAdjacents( i, j );
                sum += this.getNode( i + 1, j + 1 ).getElement().getHeating();
            }
        }

        System.out.println( "Sum of heating:" + sum );
        return sum;

    }

    /**
     * Defines the functionality of each node of the grid.
     */
    private class LinkedGridNode {

        private GridEntry      elementAdded;

        private LinkedGridNode right;
        private LinkedGridNode left;
        private LinkedGridNode above;
        private LinkedGridNode below;

        public LinkedGridNode () {
            this.elementAdded = selectInitialEntry();
            setAbove( null );
            setBelow( null );
            setRight( null );
            setLeft( null );
        }

        /**
         * Private helper method to select the initial entry for the grid entry.
         * Options supported currently are grass, forest, and water.
         *
         * @return a new specific GridEntry.
         */
        private GridEntry selectInitialEntry () {
            Random rand = new Random();
            int choosingValue = rand.nextInt( 10 );
            GridEntry initialEntry;

            if ( choosingValue < 4 ) {
                initialEntry = new GrassEntry();

            }
            else if ( choosingValue < 9 ) {
                initialEntry = new ForestEntry();
            }
            else {
                initialEntry = new WaterEntry();
            }

            return initialEntry;
        }

        /**
         * Feature to edit the specific element in the node.
         *
         * @param newElement
         *            the new element to update the node element to
         * @return the original element of the node, null if no element
         *         specified
         */
        public GridEntry changeElement ( GridEntry newElement ) {
            GridEntry original = this.elementAdded;
            this.elementAdded = newElement;
            return original;
        }

        public void setAbove ( final LinkedGridNode above ) {
            this.above = above;
        }

        public void setBelow ( final LinkedGridNode below ) {
            this.below = below;
        }

        public void setRight ( final LinkedGridNode right ) {
            this.right = right;
        }

        public void setLeft ( final LinkedGridNode left ) {
            this.left = left;
        }

        public LinkedGridNode getAbove () {
            return this.above;
        }

        public LinkedGridNode getBelow () {
            return this.below;
        }

        public LinkedGridNode getRight () {
            return this.right;
        }

        public LinkedGridNode getLeft () {
            return this.left;
        }

        public GridEntry getElement () {
            return this.elementAdded;
        }

    }
}
