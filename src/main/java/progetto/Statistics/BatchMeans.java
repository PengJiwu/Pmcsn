package progetto.Statistics;

import configuration.Configuration;
import progetto.events.Clock;
import rng.Rvms;

import java.util.ArrayList;

/**
 * This class implements Batch Means Method in order to calculate the mean
 */

public class BatchMeans {

    double n;                   // Simulation length
    int k;                      // Batch size
    int b;                      // Number of batches
    double alfa;                // Confidence parameter
    int i = 1;                  // Batch index

    private String attributeName;

    private WelfordMean welford;

    ArrayList<Double> meanlist = new ArrayList<>();

    Clock clock;

    public BatchMeans () {

        welford = new WelfordMean();
        n = Configuration.getConfiguration().duration;
        alfa = Configuration.getConfiguration().alfa;
        k = Configuration.getConfiguration().batchNumber;
        b = (int) (n/k);

        clock = Clock.getClock();

    }

    /**
     * This method takes in input a double and updates current mean using Welford Algorithm
     * @param e
     */

    public void update (double e) {

        if (clock.getCurrent() >= i * getBatchSize()) {       // check if begins a new Batch
            addMeanInList();
            incrementIndex();
        }

        calculateMean(e);

    }

    /**
     * This method takes in input a double element and adds it into the Batch
     * @param e
     */

    public void calculateMean (double e) {

        welford.addBatchElement(e);
        welford.addElement(e);

    }

    /**
     * This method adds mean in the list
     */

    public void addMeanInList () {

        double e = getMeanvalue();
        meanlist.add(e);
    }

    /**
     * This method increments Batch index and reset Welford values
     */

    public void incrementIndex (){

        i++;
        welford.resetIndexes();
        return;
    }

    /**
     * This method calculates final mean using Welford Method
     * @return
     */

    public double calculateFinalMean () {

        welford.resetIndexes();
        for (Double d: meanlist) {

            welford.addBatchElement(d);
        }

        return welford.getMean();

    }

    /**
     * This method calculate critical value for confidence interval
     * @return
     */

    public double calculateCriticalValue () {

        double t;
        Rvms rvms = new Rvms();
        t = rvms.idfStudent((k-1), 1 - (alfa/2));
        return t;

    }

    /**
     * This method calculates end points for confidence interval
     * @return
     */

    public double calculateEndPoints () {

        double t = calculateCriticalValue();
        double stdDev = Math.sqrt(welford.getTotalVariance());
        double endPoint = (t * stdDev)/ Math.sqrt(k-1);

        return  endPoint;
    }

    public double getMeanvalue () {
        return welford.getMean();
    }

    public double getBatchSize (){
        return k;
    }

    public double getTotalMean() {
        return welford.getTotal_mean();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

}
