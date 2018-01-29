package configuration;

import java.io.FileInputStream;
import java.util.Properties;

public class configuration {

    public static final String FILENAME = "../Pmcsn/src/configuration/configuration";
    public static int N = 0;
    public static int S = 0;
    public static int seed = 0;
    public static double duration = 0;
    public static int batchNumber = 0;
    public static double alfa = 0;

    public static void loadFIle() throws Exception {

        Properties parameters = new Properties();
        FileInputStream in = new FileInputStream(FILENAME);
        parameters.load(in);
        N = Integer.parseInt(parameters.getProperty("N"));
        S = Integer.parseInt(parameters.getProperty("S"));
        seed = Integer.parseInt(parameters.getProperty("seed"));
        duration = Double.parseDouble(parameters.getProperty("duration"));
        batchNumber = Integer.parseInt(parameters.getProperty("batchNumber"));
        alfa = Double.parseDouble(parameters.getProperty("alfa"));

    }
}
