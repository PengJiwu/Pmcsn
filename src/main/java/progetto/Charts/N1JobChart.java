package progetto.Charts;

import java.util.ArrayList;

public class N1JobChart extends AbstractCharts {

    protected static N1JobChart me;
    private ArrayList<XYCord> coordinates;

    private N1JobChart() {
        super();
    }

    public static N1JobChart getN1JobChart() {
        if (me == null) {

            me = new N1JobChart();
            return me;
        } else
            return me;
    }
}
