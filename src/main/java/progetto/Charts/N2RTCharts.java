package progetto.Charts;

import java.util.ArrayList;

public class N2RTCharts extends AbstractCharts {

    private ArrayList<XYCord> coordinates;

    protected static N2RTCharts me;

    public static N2RTCharts getN2JobChart(){
        if(me == null) {

            me = new N2RTCharts();
            return me;
        }
        else
            return me;
    }

    private N2RTCharts()
    {
        super();
    }
}
