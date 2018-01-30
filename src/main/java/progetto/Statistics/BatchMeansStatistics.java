package progetto.Statistics;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BatchMeansStatistics {

   // private static BatchMeans systemUtilization;                   //necessary for computation



    private static BatchMeans systemRTime;                         //requested ok
    private static BatchMeans systemClassI_RTime;                  //requested ok
    private static BatchMeans systemClassII_RTime;                 //requested ok


    private static BatchMeans systemThroughput;                    //requested ok
    private static BatchMeans systemThroughput_ClassI;             //requested ok
    private static BatchMeans systemThroughput_ClassII;            //requested ok


    private static BatchMeans cloudletThroughput;                  //requested ok
    private static BatchMeans cloudletThroughput_ClassI;           //requested ok
    private static BatchMeans cloudletThroughput_ClassII;          //requested ok


    private static BatchMeans interruptedTasks_classIIpercentage;  //requested ok
    private static BatchMeans interruptedTasks_classII_RTime;      //requested ok


    private static BatchMeans cloudletPopulation;                  //requested ok
    private static BatchMeans cloudletClassI_Population;           //requested ok
    private static BatchMeans cloudletClassII_Population;          //requested ok


    private static BatchMeans cloudPopulation;                     //requested ok
    private static BatchMeans cloudClassI_Population;              //requested ok
    private static BatchMeans cloudClassII_Population;             //requested ok


    //private static BatchMeans cloudletRTime;             //necessary for computation
    private static BatchMeans cloudletClassI_RTime;             //necessary for computation ok
    private static BatchMeans cloudletClassII_RTime;             //necessary for computation ok


    //private static BatchMeans cloudRTime;             //necessary for computation
    private static BatchMeans cloudClassI_RTime;             //necessary for computation ok
    private static BatchMeans cloudClassII_RTime;             //necessary for computation ok


    //private static BatchMeans cloudThroughput;                 //necessary for computation
    private static BatchMeans cloudThroughput_ClassI;             //necessary for computation
    private static BatchMeans cloudThroughput_ClassII;             //necessary for computation

    private static ArrayList<BatchMeans> batchMeans = new ArrayList<BatchMeans>();


    public static BatchMeans getSystemThroughput_ClassI() {
        return systemThroughput_ClassI;
    }

    public static BatchMeans getSystemThroughput_ClassII() {
        return systemThroughput_ClassII;
    }

    public static BatchMeansStatistics getMe() {

        if (me == null) {

            me = new BatchMeansStatistics();
            return me;

        }
        return me;
    }

    static BatchMeansStatistics me = null;

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

    }

    public static BatchMeans getSystemRTime() {
        return systemRTime;
    }

    public static BatchMeans getSystemClassI_RTime() {
        return systemClassI_RTime;
    }

    public static BatchMeans getSystemClassII_RTime() {
        return systemClassII_RTime;
    }

    public static BatchMeans getSystemThroughput() {
        return systemThroughput;
    }

    public static BatchMeans getCloudletThroughput() {
        return cloudletThroughput;
    }

    public static BatchMeans getCloudletThroughput_ClassI() {
        return cloudletThroughput_ClassI;
    }

    public static BatchMeans getCloudletThroughput_ClassII() {
        return cloudletThroughput_ClassII;
    }

    public static BatchMeans getInterruptedTasks_classIIpercentage() {
        return interruptedTasks_classIIpercentage;
    }

    public static BatchMeans getInterruptedTasks_classII_RTime() {
        return interruptedTasks_classII_RTime;
    }

    public static BatchMeans getCloudletPopulation() {
        return cloudletPopulation;
    }

    public static BatchMeans getCloudletClassI_Population() {
        return cloudletClassI_Population;
    }

    public static BatchMeans getCloudletClassII_Population() {
        return cloudletClassII_Population;
    }

    public static BatchMeans getCloudPopulation() {
        return cloudPopulation;
    }

    public static BatchMeans getCloudClassI_Population() {
        return cloudClassI_Population;
    }

    public static BatchMeans getCloudClassII_Population() {
        return cloudClassII_Population;
    }

    public static BatchMeans getCloudletClassI_RTime() {
        return cloudletClassI_RTime;
    }

    public static BatchMeans getCloudletClassII_RTime() {
        return cloudletClassII_RTime;
    }

    public static BatchMeans getCloudClassI_RTime() {
        return cloudClassI_RTime;
    }

    public static BatchMeans getCloudClassII_RTime() {
        return cloudClassII_RTime;
    }

    public static BatchMeans getCloudThroughput_ClassI() {
        return cloudThroughput_ClassI;
    }

    public static BatchMeans getCloudThroughput_ClassII() {
        return cloudThroughput_ClassII;
    }


    public static void printAll(){

        DecimalFormat f = new DecimalFormat("###0.000000");
        for (BatchMeans b: batchMeans){

//            double finalMean = b.calculateFinalMean();
//            double endPoints = b.calculateEndPoints();
//            double variance  = b.getVariance();

            String finalMean  = f.format(b.calculateFinalMean());
            String endPoints  = f.format(b.calculateEndPoints());
            String variance  = f.format(b.getVariance());

            System.out.println(b.getAttributeName() + finalMean + "+-" + endPoints);
            System.out.println("Variance is: " + variance);
            System.out.println("Mean is    : " + b.getTotalMean());

        }

    }



}
