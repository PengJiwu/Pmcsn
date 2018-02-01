package progetto.Charts;
import java.util.ArrayList;

public class N2JobChart extends AbstractCharts{

    private ArrayList<XYCord> coordinates;
    protected static N2JobChart me;

    public static N2JobChart getN2JobChart(){
        if(me == null) {

            me = new N2JobChart();
            return me;
        }
        else
            return me;
    }

    private N2JobChart()
    {
        super();
    }
}