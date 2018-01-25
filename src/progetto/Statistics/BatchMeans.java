package progetto.Statistics;

import configuration.configuration;

import java.util.ArrayList;

public class BatchMeans {

    private Statistics statistics;

    int n; //number of jobs
    int k; //batch size
    int b; //number of batches

    int i; //batchindex

    public double getMeanvalue() {
        return meanvalue;
    }

    public ArrayList<Double> getValuelist() {
        return valuelist;
    }

    public ArrayList<Double> getMeanlist() {
        return meanlist;
    }

    double meanvalue;

    ArrayList<Double> valuelist = new ArrayList<>();
    ArrayList<Double> meanlist = new ArrayList<>();



    public BatchMeans (Statistics stats){

        statistics = stats;
        n = stats.getTotal();
        b = configuration.batchNumber;
        k = (int) Math.floor((double) n/ (double) b);

        System.out.println("number of jobs n is " + n);
        System.out.println("number of batch is " + b);
        System.out.println("Batch size is " + k);


    }


    public void calculateMean(){

        double batchmean = 0.0;

        for (Double d: valuelist){

            batchmean += d;

        }

        batchmean /= k;
        meanlist.add(batchmean);

    }




    public double calculateFinalMean(){

        for (Double d: meanlist){
            meanvalue += d;

        }
        meanvalue /= b;
        return meanvalue;
    }





}
