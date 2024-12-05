/**
 *
 */
package heatmap.util;

import heatmap.controller.HeatMapController;

/**
 * @author Patrik Parkkila
 */
public class HeatCalculations {

    private HeatMapController             controller = HeatMapController.getInstance();

    private static final HeatCalculations instance   = new HeatCalculations();

    public double clusterCalculation () {

        return controller.getGrid().calculateAdjacents();
    }

    /**
     * Implementing singleton design
     */
    private HeatCalculations () {
    }

    /**
     * Returns the instance of the calculator if there exists one.
     *
     * @return
     */
    public static HeatCalculations getInstance () {
        return instance;
    }

}
