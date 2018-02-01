package progetto.Charts;

import java.util.ArrayList;

public class ThroughputChart extends AbstractCharts{

    protected static ThroughputChart me;

    private ArrayList<XYCord> coordinates;

    public static ThroughputChart getThroughputChart(){
        if(me == null) {

            me = new ThroughputChart();
            return me;
        }
        else
            return me;
    }

    private ThroughputChart()
    {
        super();
    }
}
