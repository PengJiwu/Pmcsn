package progetto.Charts;
import java.util.ArrayList;

public class N1JobChart extends AbstractCharts{

    private ArrayList<XYCord> coordinates;

    protected static N1JobChart me;

    public static N1JobChart getN1JobChart(){
        if(me == null) {

            me = new N1JobChart();
            return me;
        }
        else
            return me;
    }

    private N1JobChart()
    {
        super();
    }
}
