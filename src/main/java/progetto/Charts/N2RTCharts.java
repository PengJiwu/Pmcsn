package progetto.Charts;

import java.util.ArrayList;

public class N2RTCharts extends AbstractCharts {

    protected static N2RTCharts me;
    private ArrayList<XYCord> coordinates;

    private N2RTCharts() {
        super();
    }

    public static N2RTCharts getN2JobChart() {
        if (me == null) {

            me = new N2RTCharts();
            return me;
        } else
            return me;
    }
}
