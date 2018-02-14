package progetto.Charts;

import java.util.ArrayList;

public class N1RTCharts extends AbstractCharts {

    protected static N1RTCharts me;
    private ArrayList<XYCord> coordinates;

    private N1RTCharts() {
        super();
    }

    public static N1RTCharts getN1JobChart() {
        if (me == null) {

            me = new N1RTCharts();
            return me;
        } else
            return me;
    }
}
