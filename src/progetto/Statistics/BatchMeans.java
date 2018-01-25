package progetto.Statistics;

import configuration.configuration;

import java.util.ArrayList;

public class BatchMeans {

    private Statistics statistics;

    double n; //Simulation length
    int k; //batch size
    int b; //number of batches

    int i; //batchindex
    private WelfordMean welford;

    double meanvalue;

    ArrayList<Double> valuelist = new ArrayList<>();
    ArrayList<Double> meanlist = new ArrayList<>();



    public BatchMeans (){

        welford = new WelfordMean();
        n = configuration.duration;
        b = configuration.batchNumber;
        k = (int) Math.floor((double) n/ (double) b);

        System.out.println("number of jobs n is " + n);
        System.out.println("number of batch is " + b);
        System.out.println("Batch size is " + k);

    }


    public void calculateMean(double e){

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
            welford.addElement(d);

        }

        return welford.getMean();
    }


    public double getMeanvalue() {
        return welford.getMean();
    }


    public double getBatchSize(){

        return k;
    }


}
