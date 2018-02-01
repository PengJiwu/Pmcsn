package progetto.Statistics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BatchMeansStatistics {

   // private  BatchMeans systemUtilization;                   //necessary for computation



    private  BatchMeans systemRTime;                         //requested ok
    private  BatchMeans systemClassI_RTime;                  //requested ok
    private  BatchMeans systemClassII_RTime;                 //requested ok


    private  BatchMeans systemThroughput;                    //requested ok
    private  BatchMeans systemThroughput_ClassI;             //requested ok
    private  BatchMeans systemThroughput_ClassII;            //requested ok


    private  BatchMeans cloudletThroughput;                  //requested ok
    private  BatchMeans cloudletThroughput_ClassI;           //requested ok
    private  BatchMeans cloudletThroughput_ClassII;          //requested ok


    private  BatchMeans interruptedTasks_classIIpercentage;  //requested ok
    private  BatchMeans interruptedTasks_classII_RTime;      //requested ok


    private  BatchMeans cloudletPopulation;                  //requested ok
    private  BatchMeans cloudletClassI_Population;           //requested ok
    private  BatchMeans cloudletClassII_Population;          //requested ok


    private  BatchMeans cloudPopulation;                     //requested ok
    private  BatchMeans cloudClassI_Population;              //requested ok
    private  BatchMeans cloudClassII_Population;             //requested ok


    //private  BatchMeans cloudletRTime;             //necessary for computation
    private  BatchMeans cloudletClassI_RTime;             //necessary for computation ok
    private  BatchMeans cloudletClassII_RTime;             //necessary for computation ok


    //private  BatchMeans cloudRTime;             //necessary for computation
    private  BatchMeans cloudClassI_RTime;             //necessary for computation ok
    private  BatchMeans cloudClassII_RTime;             //necessary for computation ok


    //private  BatchMeans cloudThroughput;                 //necessary for computation
    private  BatchMeans cloudThroughput_ClassI;             //necessary for computation
    private  BatchMeans cloudThroughput_ClassII;             //necessary for computation


    private  BatchMeans cloudRTime;
    private  BatchMeans cloudletRTime;


    private  ArrayList<BatchMeans> batchMeans = new ArrayList<BatchMeans>();

    static BatchMeansStatistics me = null;



    public static BatchMeansStatistics getMe() {

        if (me == null) {

            me = new BatchMeansStatistics();
            return me;

        }
        return me;
    }

    private BatchMeansStatistics() {

        // systemUtilization = new BatchMeans();

        cloudThroughput_ClassI = new BatchMeans();
        cloudThroughput_ClassII = new BatchMeans();

        systemRTime = new BatchMeans();
        systemRTime.setAttributeName("System Response Time = ");
        batchMeans.add(systemRTime);


        systemClassI_RTime = new BatchMeans();
        systemClassI_RTime.setAttributeName("System Response Time for class 1 jobs = ");
        batchMeans.add(systemClassI_RTime);

        systemClassII_RTime = new BatchMeans();
        systemClassII_RTime.setAttributeName("System Response Time for class 2 jobs = ");
        batchMeans.add(systemClassII_RTime);


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


        interruptedTasks_classIIpercentage = new BatchMeans();
        interruptedTasks_classIIpercentage.setAttributeName("\nInterrupted class 2 jobs percentage = ");
        batchMeans.add(interruptedTasks_classIIpercentage);

        interruptedTasks_classII_RTime = new BatchMeans();
        interruptedTasks_classII_RTime.setAttributeName("Interrupted class 2 jobs response time = ");
        batchMeans.add(interruptedTasks_classII_RTime);


        cloudletPopulation = new BatchMeans();
        cloudletPopulation.setAttributeName("\nCloudlet Population = ");
        batchMeans.add(cloudletPopulation);

        cloudletClassI_Population = new BatchMeans();
        cloudletClassI_Population.setAttributeName("Cloudlet popupation for class 1 jobs = ");
        batchMeans.add(cloudletClassI_Population);

        cloudletClassII_Population = new BatchMeans();
        cloudletClassII_Population.setAttributeName("Cloudlet popupation for class 2 jobs = ");
        batchMeans.add(cloudletClassII_Population);


        cloudPopulation = new BatchMeans();
        cloudPopulation.setAttributeName("\nCloud population = ");
        batchMeans.add(cloudPopulation);

        cloudClassI_Population = new BatchMeans();
        cloudClassI_Population.setAttributeName("Cloud population for class 1 jobs = ");
        batchMeans.add(cloudClassI_Population);

        cloudClassII_Population = new BatchMeans();
        cloudClassII_Population.setAttributeName("Cloud population for class 2 jobs = ");
        batchMeans.add(cloudClassII_Population);


        cloudletClassI_RTime = new BatchMeans();
        cloudletClassI_RTime.setAttributeName("\nCloudlet Response Time for class 1 jobs = ");
        batchMeans.add(cloudletClassI_RTime);

        cloudletClassII_RTime = new BatchMeans();
        cloudletClassII_RTime.setAttributeName("Cloudlet Response Time for class 2 jobs = ");
        batchMeans.add(cloudletClassII_RTime);


        cloudClassI_RTime = new BatchMeans();
        cloudClassI_RTime.setAttributeName("\nCloud Response Time for class 1 jobs = ");
        batchMeans.add(cloudClassI_RTime);

        cloudClassII_RTime = new BatchMeans();
        cloudClassII_RTime.setAttributeName("Cloud Response Time for class 2 jobs = ");
        batchMeans.add(cloudClassII_RTime);

        cloudRTime = new BatchMeans();
        cloudRTime.setAttributeName("Cloud Response Time = ");
        batchMeans.add(cloudRTime);

        cloudletRTime = new BatchMeans();
        cloudletRTime.setAttributeName("Cloudlet Response Time = ");
        batchMeans.add(cloudletRTime);

    }

    public BatchMeans getSystemRTime() {
        return systemRTime;
    }

    public BatchMeans getSystemClassI_RTime() {
        return systemClassI_RTime;
    }

    public BatchMeans getSystemClassII_RTime() {
        return systemClassII_RTime;
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

    public BatchMeans getInterruptedTasks_classIIpercentage() {
        return interruptedTasks_classIIpercentage;
    }

    public BatchMeans getInterruptedTasks_classII_RTime() {
        return interruptedTasks_classII_RTime;
    }

    public BatchMeans getCloudletPopulation() {
        return cloudletPopulation;
    }

    public BatchMeans getCloudletClassI_Population() {
        return cloudletClassI_Population;
    }

    public BatchMeans getCloudletClassII_Population() {
        return cloudletClassII_Population;
    }

    public BatchMeans getCloudPopulation() {
        return cloudPopulation;
    }

    public BatchMeans getCloudClassI_Population() {
        return cloudClassI_Population;
    }

    public BatchMeans getCloudClassII_Population() {
        return cloudClassII_Population;
    }

    public BatchMeans getCloudletClassI_RTime() {
        return cloudletClassI_RTime;
    }

    public BatchMeans getCloudletClassII_RTime() {
        return cloudletClassII_RTime;
    }

    public BatchMeans getCloudClassI_RTime() {
        return cloudClassI_RTime;
    }

    public BatchMeans getCloudClassII_RTime() {
        return cloudClassII_RTime;
    }

    public BatchMeans getCloudThroughput_ClassI() {
        return cloudThroughput_ClassI;
    }

    public BatchMeans getCloudThroughput_ClassII() {
        return cloudThroughput_ClassII;
    }

    public  BatchMeans getCloudRTime() {
        return cloudRTime;
    }

    public  void setCloudRTime(BatchMeans cloudRTime) {
        this.cloudRTime = cloudRTime;
    }

    public  BatchMeans getCloudletRTime() {
        return cloudletRTime;
    }

    public  void setCloudletRTime(BatchMeans cloudletRTime) {
        this.cloudletRTime = cloudletRTime;
    }

    public  BatchMeans getSystemThroughput_ClassI() {
        return systemThroughput_ClassI;
    }

    public  BatchMeans getSystemThroughput_ClassII() {
        return systemThroughput_ClassII;
    }



    public  void printAll(){

        DecimalFormat f = new DecimalFormat("###0.000000");
        for (BatchMeans b: batchMeans){

            double total_mean = b.getTotalMean();
            String finalMean  = f.format(b.calculateFinalMean());
            String endPoints  = f.format(b.calculateEndPoints());
            System.out.println(b.getAttributeName() + finalMean + "+-" + endPoints);
            System.out.println("total mean is:" + total_mean);

        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/main/java/progetto/Results/prova.json"),batchMeans);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
