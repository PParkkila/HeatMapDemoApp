/**
 *
 */
package heatmap.controller;

import heatmap.model.BuildingEntry;
import heatmap.model.ForestEntry;
import heatmap.model.GrassEntry;
import heatmap.model.GridEntry;
import heatmap.model.RoadEntry;
import heatmap.model.WaterEntry;
import heatmap.util.LinkedGrid;

/**
 * Implementing a singleton design. Only one applicable at one time.
 * @author Patrik Parkkila
 */
public class HeatMapController {

    /**
     * Instance of the heat map controller to be used.
     */
    private static final HeatMapController instance = new HeatMapController();

    private volatile LinkedGrid            grid;
    
    

    /**
     * Empty constructor to create an instance of the HeatMapController.
     */
    private HeatMapController () {
    }

    /**
     * Allows for return of the HeatMapController instance for singleton
     * purposes.
     *
     * @return the instance of HeatMapController.
     */
    public static HeatMapController getInstance () {
        return instance;
    }

    public void generateGrid () {

        grid = new LinkedGrid();

        for ( int i = 0; i < 9; i++ ) {
            grid.addColumnRight();
            grid.addRowBottom();
        }
    }

    /**
     * Allows a user to update the entry of a given cell by a string name.
     *
     * @param row
     * @param column
     * @param type
     * @return the extension to the image for the current entry
     */
    public String updateEntry ( int row, int column, String type ) {

        String searched = type.toLowerCase();

        if ( searched.equals( "grass" ) ) {
            GridEntry entry = new GrassEntry();
            grid.updateElement( row, column, entry );
            grid.printGridNames();
            return entry.getFilePath();
        }
        else if ( searched.equals( "road" ) ) {
            GridEntry entry = new RoadEntry();
            grid.updateElement( row, column, entry );
            grid.printGridNames();
            return entry.getFilePath();
        }
        else if ( searched.equals( "forest" ) ) {
            GridEntry entry = new ForestEntry();
            grid.updateElement( row, column, entry );
            grid.printGridNames();
            return entry.getFilePath();
        }
        else if ( searched.equals( "building" ) ) {
            GridEntry entry = new BuildingEntry();
            grid.updateElement( row, column, entry );
            grid.printGridNames();
            return entry.getFilePath();
        }
        else if ( searched.equals( "water" ) ) {
            GridEntry entry = new WaterEntry();
            grid.updateElement( row, column, entry );
            grid.printGridNames();
            return entry.getFilePath();
        }
        else {
            return null;
        }

    }

    public String getFilePath ( int row, int col ) {
        return grid.getElement( row, col ).getFilePath();
    }

    /**
     * Retrieves the entry at this location in the grid. Inputs need to be
     * scaled starting at 1.
     *
     * @param row
     * @param col
     * @return
     */
    public GridEntry getEntry ( int row, int col ) {
        return grid.getElement( row, col );
    }

    public LinkedGrid getGrid () {
        return this.grid;
    }

}
