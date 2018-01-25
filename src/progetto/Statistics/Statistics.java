package progetto.Statistics;

import configuration.configuration;
import progetto.MmccArea;
import progetto.events.Clock;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class Statistics {


    int N = configuration.N;
    int S = configuration.S;
    double STOP = configuration.duration;

    private MmccArea areaN1Cloudlet;
    private MmccArea areaN2Cloudlet;
    private MmccArea areatotCloudlet;

    private MmccArea areaN1Cloud;
    private MmccArea areaN2Cloud;
    private MmccArea areatotCloud;

    private int cloudletN1 = 0, cloudletN2 = 0;

    private int cloudN1 = 0, cloudN2 = 0;

    private int completedN1cloudlet = 0;
    private int completedN2cloudlet = 0;

    private int completedN1cloud = 0;
    private int completedN2cloud = 0;

    private int totalN1 = 0;
    private int totalN2 = 0;

    private int total;

    private static Statistics me = null;






    private Clock clock = Clock.getClock();

    private Statistics(){

        areaN1Cloudlet = new MmccArea();
        areaN2Cloudlet = new MmccArea();
        areatotCloudlet = new MmccArea();
        areaN1Cloud = new MmccArea();
        areaN2Cloud = new MmccArea();
        areatotCloud = new MmccArea();

    }

    public static Statistics getMe() {
        if (me == null){

            me = new Statistics();
            return  me;

        }

        return me;
    }

    public void updateCloudletStatistics(int a, int b, int c, int d, int e, int f)
    {

        cloudletN1 = a;
        cloudletN2 = b;

        totalN1 = c;
        totalN2 = d;

        completedN1cloudlet = e;
        completedN2cloudlet = f;


      //  if (cloudletN1 > 0) {                               /* update integrals  */
            areaN1Cloudlet.node += (clock.getCurrent() - clock.getPrevious()) * cloudletN1;
            areaN1Cloudlet.service += (clock.getCurrent() - clock.getPrevious());
       // }

        //if (cloudletN2 > 0) {                               /* update integrals  */
            areaN2Cloudlet.node += (clock.getCurrent() - clock.getPrevious()) * cloudletN2;
            areaN2Cloudlet.service += clock.getCurrent() - clock.getPrevious();
     //   }

        //if (cloudletN1 + cloudletN2 > 0) {                               /* update integrals  */
            areatotCloudlet.node += (clock.getCurrent() - clock.getPrevious()) * (cloudletN1 + cloudletN2);
            areatotCloudlet.service += clock.getCurrent() - clock.getPrevious();
     //   }


    }


    public void updateCloudStatistics(int n1, int n2, int completedN1, int completedN2) {

        cloudN1 = n1;
        cloudN2 = n2;

        completedN1cloud = completedN1;
        completedN2cloud = completedN2;

        if (cloudN1 > 0) {                               /* update integrals  */
            areaN1Cloud.node += (clock.getCurrent() - clock.getPrevious()) * cloudN1;
            areaN1Cloud.service += (clock.getCurrent() - clock.getPrevious());
        }

        if (cloudN2 > 0) {                               /* update integrals  */
            areaN2Cloud.node += (clock.getCurrent() - clock.getPrevious()) * cloudN2;
            areaN2Cloud.service += clock.getCurrent() - clock.getPrevious();
        }

        if (cloudN1 + cloudN2 > 0) {                               /* update integrals  */
            areatotCloud.node += (clock.getCurrent() - clock.getPrevious()) * (cloudN1 + cloudN1);
            areatotCloud.service += clock.getCurrent() - clock.getPrevious();
        }


    }





    public void printStatistics() {

        DecimalFormat f = new DecimalFormat("###0.00");

        total = totalN1 + totalN2;

        System.out.println("\n job totali:  " + totalN1 + " & " + totalN2 );
        System.out.println("\n job totali:  " + total );

        System.out.println("job class 1 completati dal cloudlet " + completedN1cloudlet);
        System.out.println("job class 2 completati dal cloudlet " + completedN2cloudlet);

        System.out.println("job class 1 completati dal cloud " + completedN1cloud);
        System.out.println("job class 2 completati dal cloud " + completedN2cloud);

        int cloudletTotCompl = completedN1cloudlet + completedN2cloudlet;
        double thr = cloudletTotCompl / areatotCloudlet.service;
        System.out.println("Throughput del cloudlet: " + thr);


        System.out.println("   average interarrival time  class 1 =   " + f.format(clock.getLast() / totalN1));
        System.out.println("   average interarrival time  class 2 =   " + f.format(clock.getLast() / totalN2));
        System.out.println("   average interarrival time  tot     =   " + f.format(clock.getLast() / total));

        //TODO CHECK IF CORRECT!!!
//        System.out.println("   average wait class1............ =   " + f.format(areaN1.node / completedN1));
//        System.out.println("   average wait class2............ =   " + f.format(areaN2.node / completedN2));
//        System.out.println("   average wait tot............ =      " + f.format(areaTot.node / (completedN1 + completedN2)));

        System.out.println("   average service time class 1 cloudlet .... =   " + f.format(areaN1Cloudlet.service / completedN1cloudlet));
        System.out.println("   average service time class 2 cloudlet.... =    " + f.format(areaN2Cloudlet.service / completedN2cloudlet));
        System.out.println("   average service time tot.... =        " + f.format(areatotCloudlet.service / (completedN1cloudlet + completedN2cloudlet)));

        System.out.println("   average service time class 1 cloud   .... =   " + f.format(areaN1Cloud.service / completedN1cloud));
        System.out.println("   average service time class 2 cloud   .... =    " + f.format(areaN2Cloud.service / completedN2cloud));
        System.out.println("   average service time tot.... =        " + f.format(areatotCloud.service / (completedN1cloud + completedN2cloud)));

        System.out.println("   average class 1 # in the cloudlet ... =   " + f.format(areaN1Cloudlet.node / clock.getCurrent()));
        System.out.println("   average class 2 # in the cloudlet ... =   " + f.format(areaN2Cloudlet.node / clock.getCurrent()));
        System.out.println("   average total # in the cloudlet ... =   " + f.format(areatotCloudlet.node / clock.getCurrent()));


        System.out.println("   average class 1 # in the cloud ... =   " + f.format(areaN1Cloud.node / clock.getCurrent()));
        System.out.println("   average class 2 # in the cloud... =   " + f.format(areaN2Cloud.node / clock.getCurrent()));
        System.out.println("   average total # in the cloud ... =   " + f.format(areatotCloud.node / clock.getCurrent()));

        System.out.println("   utilization classe 1  ............. =   " + f.format(areaN1Cloudlet.service / clock.getCurrent()));
        System.out.println("   utilization classe 2............. =   " + f.format(areaN2Cloudlet.service / clock.getCurrent()));
        System.out.println("   utilization ............. =   " + f.format(areatotCloudlet.service / clock.getCurrent()));

        System.out.println("------------------------------------------------------------------------------------------------------------------------------");

        BatchMeans bm = new BatchMeans((this));

    }


    public void createFile() throws FileNotFoundException, UnsupportedEncodingException {

        DecimalFormat f = new DecimalFormat("###0.0000");

        int total = totalN1 + totalN2;
        int seed = configuration.seed;
        String path = "../Pmcsn/src/progetto/Results/";

        PrintWriter writer = new PrintWriter(path + "StatN"+ N + "S" + S +"seed" + seed + "STOP" + STOP + ".txt", "UTF-8");
        writer.append("Statistics" + "\n");
        writer.append("N = " + N + "\n");
        writer.append("S = " + S + "\n");
        writer.append("Numbers of job of I class in cludlet" + totalN1 + "\n");
        writer.append("Numbers of job of II class in cloudlet" + totalN2 + "\n");
        writer.append("Numbers of job of I class in cloud" + completedN1cloud + "\n");
        writer.append("Numbers of job of II class in cloud" + completedN2cloud + "\n");


        writer.append("   average interarrival time  class 2 =   " + f.format(clock.getLast() / totalN2) + "\n");
        writer.append("   average interarrival time  class 1 =   " + f.format(clock.getLast() / totalN1) + "\n");
        writer.append("   average interarrival time  tot     =   " + f.format(clock.getLast() / total) + "\n");


        writer.append("   average service time class 1 cloudlet .... =   " + f.format(areaN1Cloudlet.service / completedN1cloudlet) + "\n");
        writer.append("   average service time class 2 cloudlet.... =    " + f.format(areaN2Cloudlet.service / completedN2cloudlet) + "\n");
        writer.append("   average service time tot.... =        " + f.format(areatotCloudlet.service / (completedN1cloudlet + completedN2cloudlet)) + "\n");

        writer.append("   average service time class 1 cloud   .... =   " + f.format(areaN1Cloud.service / completedN1cloud) + "\n");
        writer.append("   average service time class 2 cloud   .... =    " + f.format(areaN2Cloud.service / completedN2cloud) + "\n");
        writer.append("   average service time tot.... =        " + f.format(areatotCloud.service / (completedN1cloud + completedN2cloud)) + "\n");

        writer.append("   average class 1 # in the cloudlet ... =   " + f.format(areaN1Cloudlet.node / clock.getCurrent()) + "\n");
        writer.append("   average class 2 # in the cloudlet ... =   " + f.format(areaN2Cloudlet.node / clock.getCurrent()) + "\n");
        writer.append("   average total # in the cloudlet ... =   " + f.format(areatotCloudlet.node / clock.getCurrent()) + "\n");


        writer.append("   average class 1 # in the cloud ... =   " + f.format(areaN1Cloud.node / clock.getCurrent()) + "\n");
        writer.append("   average class 2 # in the cloud... =   " + f.format(areaN2Cloud.node / clock.getCurrent()) + "\n");
        writer.append("   average total # in the cloud ... =   " + f.format(areatotCloud.node / clock.getCurrent()) + "\n");

        writer.append("   utilization classe 1  ............. =   " + f.format(areaN1Cloudlet.service / clock.getCurrent()) + "\n");
        writer.append("   utilization classe 2............. =   " + f.format(areaN2Cloudlet.service / clock.getCurrent()) + "\n");
        writer.append("   utilization ............. =   " + f.format(areatotCloudlet.service / clock.getCurrent()) + "\n");

        writer.append(("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^") + "\n\n");

        writer.close();



    }


    //Getter and setter
    public int getCloudletN1() {
        return cloudletN1;
    }

    public void setCloudletN1(int cloudletN1) {
        this.cloudletN1 = cloudletN1;
    }

    public int getCloudletN2() {
        return cloudletN2;
    }

    public void setCloudletN2(int cloudletN2) {
        this.cloudletN2 = cloudletN2;
    }

    public int getCloudN1() {
        return cloudN1;
    }

    public void setCloudN1(int cloudN1) {
        this.cloudN1 = cloudN1;
    }

    public int getCloudN2() {
        return cloudN2;
    }

    public void setCloudN2(int cloudN2) {
        this.cloudN2 = cloudN2;
    }

    public int getCompletedN1cloudlet() {
        return completedN1cloudlet;
    }

    public void setCompletedN1cloudlet(int completedN1cloudlet) {
        this.completedN1cloudlet = completedN1cloudlet;
    }

    public int getCompletedN2cloudlet() {
        return completedN2cloudlet;
    }

    public void setCompletedN2cloudlet(int completedN2cloudlet) {
        this.completedN2cloudlet = completedN2cloudlet;
    }

    public int getCompletedN1cloud() {
        return completedN1cloud;
    }

    public void setCompletedN1cloud(int completedN1cloud) {
        this.completedN1cloud = completedN1cloud;
    }

    public int getCompletedN2cloud() {
        return completedN2cloud;
    }

    public void setCompletedN2cloud(int completedN2cloud) {
        this.completedN2cloud = completedN2cloud;
    }

    public int getTotalN1() {
        return totalN1;
    }

    public void setTotalN1(int totalN1) {
        this.totalN1 = totalN1;
    }

    public int getTotalN2() {
        return totalN2;
    }

    public void setTotalN2(int totalN2) {
        this.totalN2 = totalN2;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



}
