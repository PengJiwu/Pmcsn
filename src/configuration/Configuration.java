package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import progetto.events.Clock;

import java.io.File;
import java.io.IOException;

public class Configuration {
    public int N = 0;
    public int S = 0;
    public int seed = 0;
    public double duration = 0;
    public int batchNumber = 0;
    public double alfa = 0;

    static Configuration me = null;

    public static Configuration getConfiguration(){
        if(me == null)
        {
            me = new Configuration();
            ObjectMapper mapper = new ObjectMapper();

            try {
                me = mapper.readValue(new File("../Pmcsn/src/configuration/JSONConfiguration"), Configuration.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return me;
        }
        else
            return me;
    }

    private Configuration()
    {

    }



    @Override
    public String toString() {
        return "Configuration{" +
                "N=" + N +
                ", S=" + S +
                ", seed=" + seed +
                ", duration=" + duration +
                ", batchNumber=" + batchNumber +
                ", alfa=" + alfa +
                '}';
    }

    public int getN() {
        return N;
    }

    public int getS() {
        return S;
    }

    public int getSeed() {
        return seed;
    }

    public double getDuration() {
        return duration;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public double getAlfa() {
        return alfa;
    }
}




