package progetto.Charts;

import java.util.ArrayList;

public class RTCharts extends AbstractCharts{
    private ArrayList<XYCord> coordinates;

    protected static RTCharts me;

    public static RTCharts getRTCharts(){
        if(me == null) {

            me = new RTCharts();
            return me;
        }
        else
            return me;
    }

    private RTCharts()
    {
        super();
    }

}
