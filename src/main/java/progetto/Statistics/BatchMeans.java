package progetto.Statistics;

import configuration.Configuration;
import progetto.events.Clock;
import rng.Rvms;

import java.util.ArrayList;

public class BatchMeans {

    private Statistics statistics;

    double n;      //Simulation length
    int k;         //batch size
    int b;     //number of batches
    double alfa;  //confidence parameter

    int i = 1; //batch index

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    private String attributeName;

    private WelfordMean welford;

    double meanvalue;

    ArrayList<Double> valuelist = new ArrayList<>();
    ArrayList<Double> meanlist = new ArrayList<>();
    Clock clock;


    public BatchMeans () {

        welford = new WelfordMean();
        n = Configuration.getConfiguration().duration;
        alfa = Configuration.getConfiguration().alfa;
        k = Configuration.getConfiguration().batchNumber;
        b = (int) (n/k);

        clock = Clock.getClock();


//        System.out.println("number of jobs n is " + n);
//        System.out.println("number of batch is " + b);
//        System.out.println("Batch size is " + k);
    }

    public void update(double e){
        if (clock.getCurrent() >= i*getBatchSize())
        {
            addMeanInList();
            incrementIndex();
        }
        calculateMean(e);
    }


    public void calculateMean(double e){

        welford.addBatchElement(e);
        welford.addElement(e);

    }

    public void addMeanInList(){

        double e = getMeanvalue();
        meanlist.add(e);

    }


    public void incrementIndex(){

        i++;
        welford.resetIndexes();
        return;
    }



    public double calculateFinalMean(){

        for (Double d: meanlist){
            welford.addBatchElement(d);

        }

        return welford.getMean();
    }


    public double getMeanvalue() {
        return welford.getMean();
    }


    public double getBatchSize(){

        return k;
    }


    public double calculateCriticalValue(){

        double t;
        Rvms rvms = new Rvms();
        t = rvms.idfStudent((k-1), 1-(alfa/2));
        return t;

    }

    public double calculateEndPoints(){

        double t = calculateCriticalValue();
        double stdDev = Math.sqrt(welford.getTotalVariance());
        double endPoint = (t*stdDev)/ Math.sqrt(k-1);

        return  endPoint;
    }

    public double getVariance(){

        return welford.getTotalVariance();
    }


    public double getTotalMean(){

        return welford.getTotal_mean();
    }


}
