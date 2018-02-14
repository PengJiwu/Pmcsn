package progetto.Charts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Populations {

    protected static Populations me;
    @JsonSerialize
    private ArrayList<Coordinates> coordinates;

    private Populations() {

        coordinates = new ArrayList<>();

    }

    public static Populations getPopulations() {
        if (me == null) {

            me = new Populations();
            return me;
        } else
            return me;
    }

    public void updateCord(Coordinates coordinates) {
        this.coordinates.add(coordinates);
    }

    public void printJSON(String name) {


        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/main/java/progetto/Results/" + name + ".json"), this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printALL() {

        for (Object ob : coordinates) {
            System.out.println(((Coordinates) ob));


        }


    }


}
