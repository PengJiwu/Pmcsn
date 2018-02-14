package progetto.Charts;

import java.util.ArrayList;

public class RTCharts extends AbstractCharts {
    protected static RTCharts me;
    private ArrayList<XYCord> coordinates;

    private RTCharts() {
        super();
    }

    public static RTCharts getRTCharts() {
        if (me == null) {

            me = new RTCharts();
            return me;
        } else
            return me;
    }

}
