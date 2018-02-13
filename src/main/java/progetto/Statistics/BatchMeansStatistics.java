package progetto.Statistics;

import com.fasterxml.jackson.databind.ObjectMapper;
import progetto.Charts.Coordinates;
import progetto.Charts.Populations;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BatchMeansStatistics {

    private  BatchMeans systemRTime;
    private  BatchMeans systemRTime_ClassI;
    private  BatchMeans systemRTime_ClassII;


    private  BatchMeans systemThroughput;
    private  BatchMeans systemThroughput_ClassI;
    private  BatchMeans systemThroughput_ClassII;

    private  BatchMeans cloudletThroughput;
    private  BatchMeans cloudletThroughput_ClassI;
    private  BatchMeans cloudletThroughput_ClassII;

    private  BatchMeans interruptedTasksPercentage_ClassII;
    private  BatchMeans interruptedTasksRTime_ClassII;

    private  BatchMeans cloudletPopulation;
    private  BatchMeans cloudletPopulation_ClassI;
    private  BatchMeans cloudletPopulation_ClassII;

    private  BatchMeans cloudPopulation;
    private  BatchMeans cloudPopulation_ClassI;
    private  BatchMeans cloudPopulation_ClassII;

    private  BatchMeans cloudletRTime_ClassI;
    private  BatchMeans cloudletRTime_ClassII;

    private  BatchMeans cloudRTime_ClassI;
    private  BatchMeans cloudRTime_ClassII;

    private  BatchMeans cloudThroughput_ClassI;
    private  BatchMeans cloudThroughput_ClassII;

    private  BatchMeans cloudRTime;
    private  BatchMeans cloudletRTime;

    private BatchMeans sentToTheCloudJobs;
    private BatchMeans sentToTheCloudJobs_Class1;
    private BatchMeans sentToTheCloudJobs_Class2;

    private  ArrayList<BatchMeans> batchMeans = new ArrayList<BatchMeans>();

    static BatchMeansStatistics me = null;

    /**
     * This method implements the singleton pattern for BatchMean Statistics
     * @return
     */

    public static BatchMeansStatistics getMe () {

        if (me == null) {

            me = new BatchMeansStatistics();
            return me;

        }
        return me;
    }

    /**
     * This class is a repository for all needed Batch Mean
     */

    private BatchMeansStatistics () {

        cloudThroughput_ClassI = new BatchMeans();
        cloudThroughput_ClassII = new BatchMeans();

        systemRTime = new BatchMeans();
        systemRTime.setAttributeName("System Response Time = ");
        batchMeans.add(systemRTime);


        systemRTime_ClassI = new BatchMeans();
        systemRTime_ClassI.setAttributeName("System Response Time for class 1 jobs = ");
        batchMeans.add(systemRTime_ClassI);

        systemRTime_ClassII = new BatchMeans();
        systemRTime_ClassII.setAttributeName("System Response Time for class 2 jobs = ");
        batchMeans.add(systemRTime_ClassII);


        systemThroughput = new BatchMeans();
        systemThroughput.setAttributeName("\nSystem Throughput = ");
        batchMeans.add(systemThroughput);

        systemThroughput_ClassI = new BatchMeans();
        systemThroughput_ClassI.setAttributeName("System Throughput for class 1 jobs = ");
        batchMeans.add(systemThroughput_ClassI);

        systemThroughput_ClassII = new BatchMeans();
        systemThroughput_ClassII.setAttributeName("System Throughput for class 2 jobs = ");
        batchMeans.add(systemThroughput_ClassII);


        cloudletThroughput = new BatchMeans();
        cloudletThroughput.setAttributeName("\nCloudlet Throughput = ");
        batchMeans.add(cloudletThroughput);

        cloudletThroughput_ClassI = new BatchMeans();
        cloudletThroughput_ClassI.setAttributeName("Cloudlet Throughput of class 1 jobs = ");
        batchMeans.add(cloudletThroughput_ClassI);

        cloudletThroughput_ClassII = new BatchMeans();
        cloudletThroughput_ClassII.setAttributeName("Cloudlet Throughput of class 2 jobs = ");
        batchMeans.add(cloudletThroughput_ClassII);


        interruptedTasksPercentage_ClassII = new BatchMeans();
        interruptedTasksPercentage_ClassII.setAttributeName("\nInterrupted class 2 jobs percentage = ");
        batchMeans.add(interruptedTasksPercentage_ClassII);

        interruptedTasksRTime_ClassII = new BatchMeans();
        interruptedTasksRTime_ClassII.setAttributeName("Interrupted class 2 jobs response time = ");
        batchMeans.add(interruptedTasksRTime_ClassII);


        cloudletPopulation = new BatchMeans();
        cloudletPopulation.setAttributeName("\nCloudlet Population = ");
        batchMeans.add(cloudletPopulation);

        cloudletPopulation_ClassI = new BatchMeans();
        cloudletPopulation_ClassI.setAttributeName("Cloudlet population for class 1 jobs = ");
        batchMeans.add(cloudletPopulation_ClassI);

        cloudletPopulation_ClassII = new BatchMeans();
        cloudletPopulation_ClassII.setAttributeName("Cloudlet population for class 2 jobs = ");
        batchMeans.add(cloudletPopulation_ClassII);


        cloudPopulation = new BatchMeans();
        cloudPopulation.setAttributeName("\nCloud population = ");
        batchMeans.add(cloudPopulation);

        cloudPopulation_ClassI = new BatchMeans();
        cloudPopulation_ClassI.setAttributeName("Cloud population for class 1 jobs = ");
        batchMeans.add(cloudPopulation_ClassI);

        cloudPopulation_ClassII = new BatchMeans();
        cloudPopulation_ClassII.setAttributeName("Cloud population for class 2 jobs = ");
        batchMeans.add(cloudPopulation_ClassII);


        cloudletRTime_ClassI = new BatchMeans();
        cloudletRTime_ClassI.setAttributeName("\nCloudlet Response Time for class 1 jobs = ");
        batchMeans.add(cloudletRTime_ClassI);

        cloudletRTime_ClassII = new BatchMeans();
        cloudletRTime_ClassII.setAttributeName("Cloudlet Response Time for class 2 jobs = ");
        batchMeans.add(cloudletRTime_ClassII);

        cloudletRTime = new BatchMeans();
        cloudletRTime.setAttributeName("Cloudlet Response Time = ");
        batchMeans.add(cloudletRTime);


        cloudRTime_ClassI = new BatchMeans();
        cloudRTime_ClassI.setAttributeName("\nCloud Response Time for class 1 jobs = ");
        batchMeans.add(cloudRTime_ClassI);

        cloudRTime_ClassII = new BatchMeans();
        cloudRTime_ClassII.setAttributeName("Cloud Response Time for class 2 jobs = ");
        batchMeans.add(cloudRTime_ClassII);

        cloudRTime = new BatchMeans();
        cloudRTime.setAttributeName("Cloud Response Time = ");
        batchMeans.add(cloudRTime);



        sentToTheCloudJobs = new BatchMeans();
        sentToTheCloudJobs.setAttributeName("Number of jobs sent to the cloud is = ");
        batchMeans.add(sentToTheCloudJobs);

        sentToTheCloudJobs_Class1 = new BatchMeans();
        sentToTheCloudJobs_Class1.setAttributeName("Number of class 1 jobs sent to the cloud is = ");
        batchMeans.add(sentToTheCloudJobs_Class1);

        sentToTheCloudJobs_Class2 = new BatchMeans();
        sentToTheCloudJobs_Class2.setAttributeName("Number of class 2 jobs sent to the cloud is = ");
        batchMeans.add(sentToTheCloudJobs_Class2);


    }

    public BatchMeans getSentToTheCloudJobs() {
        return sentToTheCloudJobs;
    }

    public BatchMeans getSystemRTime() {
        return systemRTime;
    }

    public BatchMeans getSystemRTime_ClassI() {
        return systemRTime_ClassI;
    }

    public BatchMeans getSystemRTime_ClassII() {
        return systemRTime_ClassII;
    }

    public BatchMeans getSystemThroughput() {
        return systemThroughput;
    }

    public BatchMeans getCloudletThroughput() {
        return cloudletThroughput;
    }

    public BatchMeans getCloudletThroughput_ClassI() {
        return cloudletThroughput_ClassI;
    }

    public BatchMeans getCloudletThroughput_ClassII() {
        return cloudletThroughput_ClassII;
    }

    public BatchMeans getInterruptedTasksPercentage_ClassII() {
        return interruptedTasksPercentage_ClassII;
    }

    public BatchMeans getInterruptedTasksRTime_ClassII() {
        return interruptedTasksRTime_ClassII;
    }

    public BatchMeans getCloudletPopulation() {
        return cloudletPopulation;
    }

    public BatchMeans getCloudletPopulation_ClassI() {
        return cloudletPopulation_ClassI;
    }

    public BatchMeans getCloudletPopulation_ClassII() {
        return cloudletPopulation_ClassII;
    }

    public BatchMeans getCloudPopulation() {
        return cloudPopulation;
    }

    public BatchMeans getCloudPopulation_ClassI() {
        return cloudPopulation_ClassI;
    }

    public BatchMeans getCloudPopulation_ClassII() {
        return cloudPopulation_ClassII;
    }

    public BatchMeans getCloudletRTime_ClassI() {
        return cloudletRTime_ClassI;
    }

    public BatchMeans getCloudletRTime_ClassII() {
        return cloudletRTime_ClassII;
    }

    public BatchMeans getCloudRTime_ClassI() {
        return cloudRTime_ClassI;
    }

    public BatchMeans getCloudRTime_ClassII() {
        return cloudRTime_ClassII;
    }

    public  BatchMeans getCloudRTime() {
        return cloudRTime;
    }

    public  BatchMeans getCloudletRTime() {
        return cloudletRTime;
    }

    public  BatchMeans getSystemThroughput_ClassI() {
        return systemThroughput_ClassI;
    }

    public  BatchMeans getSystemThroughput_ClassII() {
        return systemThroughput_ClassII;
    }

    public BatchMeans getSentToTheCloudJobs_Class1() {
        return sentToTheCloudJobs_Class1;
    }

    public BatchMeans getSentToTheCloudJobs_Class2() {
        return sentToTheCloudJobs_Class2;
    }

    /**
     * This method print all statistics
     */

    public  void printAll (){

        DecimalFormat f = new DecimalFormat("###0.000000");
        for (BatchMeans b: batchMeans){

            double total_mean = b.getTotalMean();
            String finalMean  = f.format(b.calculateFinalMean());
            String endPoints  = f.format(b.calculateEndPoints());
            System.out.println(b.getAttributeName() + finalMean + "+-" + endPoints);
            System.out.println("Total mean is: " + total_mean);

        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/main/java/progetto/Results/prova.json"),batchMeans);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void printPop (){

        int i = 0;

        while(i<64){

            Coordinates coordinates = new Coordinates();
            coordinates.setTime(i);
            for (BatchMeans b: batchMeans) {
                if (b.getAttributeName().equals("Cloudlet population for class 1 jobs = "))
                    coordinates.setCloudletPopulation_n1(b.meanlist.get(i));
                if (b.getAttributeName().equals("Cloudlet population for class 2 jobs = "))
                    coordinates.setCloudletPopulation_n2(b.meanlist.get(i));
                if (b.getAttributeName().equals("Cloud population for class 1 jobs = "))
                    coordinates.setCloudPopulation_n1(b.meanlist.get(i));
                if (b.getAttributeName().equals("Cloud population for class 2 jobs = "))
                    coordinates.setCloudPopulation_n2(b.meanlist.get(i));
            }
            Populations.getPopulations().updateCord(coordinates);
        i++;
        }

    }

}
