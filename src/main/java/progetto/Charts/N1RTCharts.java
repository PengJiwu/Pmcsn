package progetto.Charts;

import java.util.ArrayList;

public class N1RTCharts extends AbstractCharts{

    private ArrayList<XYCord> coordinates;

    protected static N1RTCharts me;

    public static N1RTCharts getN1JobChart(){
        if(me == null) {

            me = new N1RTCharts();
            return me;
        }
        else
            return me;
    }

    private N1RTCharts()
    {
        super();
    }
}
