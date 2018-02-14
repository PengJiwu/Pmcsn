package progetto.Charts;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractCharts {


    protected ArrayList<XYCord> coordinates;

    public AbstractCharts() {
        coordinates = new ArrayList<XYCord>();
    }

    public void addCoordinates(double x, double y) {
        coordinates.add(new XYCord(x, y));
    }


    public ArrayList<XYCord> getCoordinates() {
        return coordinates;
    }


    public void printJSON(String name) {


        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/main/java/progetto/Results/" + name + ".json"), this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
