package progetto.Charts;

import java.util.ArrayList;

public class N2JobChart extends AbstractCharts {

    protected static N2JobChart me;
    private ArrayList<XYCord> coordinates;

    private N2JobChart() {
        super();
    }

    public static N2JobChart getN2JobChart() {
        if (me == null) {

            me = new N2JobChart();
            return me;
        } else
            return me;
    }
}