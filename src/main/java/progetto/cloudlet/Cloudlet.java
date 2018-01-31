package progetto.cloudlet;


import progetto.Job;
import progetto.MmccArea;
import progetto.Statistics.BatchMeansStatistics;
import progetto.Statistics.Statistics;
import progetto.cloud.Cloud;
import progetto.events.*;
import rng.Rvgs;

import java.text.DecimalFormat;

public class Cloudlet {


    int n1,n2;
    int N,S;
    int totalN1,totalN2;


    EventList eventList;
    static int completedN1;
    static int completedN2;
    int interruptedJobs;

    MmccArea areaN1;
    MmccArea areaN2;
    MmccArea areaTot;
    Clock clock;

    Rvgs r; // Needed to create an exponential setup time

    static double MU1cloud = 0.25;
    static double MU2cloud = 0.22;

    public Cloudlet(int N,int S, Rvgs rvgs){

        n1 = 0;
        n2 = 0;
        completedN1 = 0;
        completedN2 = 0;
        interruptedJobs = 0;
        totalN1 = 0;
        totalN2 = 0;

        this.N = N;
        this.S = S;
        this.r = rvgs;

        eventList = EventList.getEventList();

        areaN1 = new MmccArea();
        areaN2 = new MmccArea();
        areaTot = new MmccArea();
        areaN1.initAreaParas();
        areaN2.initAreaParas();
        areaTot.initAreaParas();

        clock = Clock.getClock();
    }


    public void processArrival(CloudletArrivalEvent event)
    {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        Job nextArrivalJob = event.getJob();
        if (nextArrivalJob.getClasse() == 1) {

            //Arriva job di classe 1
            totalN1++;

            if (n1 == N)                          //Cloudlet is completely full
                sendToTheCloud(nextArrivalJob);

            else if (n1 + n2 < S) {               // Threshold hasn't been trespassed yet

                n1++;

                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletClassI_Population().update(n1);

                createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());

            } else if (n2 > 0) {                    // Threshold has been passed and there's at least one class 2 job

                n1++;
                n2--;

                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletClassI_Population().update(n1);
                bm.getCloudletClassII_Population().update(n2);

                Job job = eventList.removeOneC2CompletionEvent();
                interruptedJobs++;

                System.out.println("Number of interrupted jobs: " + interruptedJobs );

                job.setPrelation(true);
                sendToTheCloud(job);
                createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());

                bm.getInterruptedTasks_classIIpercentage().update((double) interruptedJobs/totalN2);

            } else {                                // Threshold has been passed and there aren't class 2 jobs

                n1++;

                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletClassI_Population().update(n1);

                createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
            }
        }
        else {

            totalN2++;
            //Arriva job di classe 2

            if (n1 + n2 >= S) {
                sendToTheCloud(nextArrivalJob);
            } else {

                n2++;

                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletClassII_Population().update(n2);

                createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
            }
        }
    }

    private void createNewCompletionEvent(Job job, double timeOfEvent) {

        Event e = new CloudletCompletionEvent(job,timeOfEvent);

        eventList.pushEvent(eventList.getCloudletEventList(), e);
    }

    public void processCompletion(CloudletCompletionEvent event) {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        if (event.getJob().getClasse() == 1) {

            n1--;
            completedN1++;

            bm.getCloudletPopulation().update(n1 + n2);
            bm.getCloudletClassI_Population().update(n1);

            double cloudletClassIRTime = event.getJob().getService_time();

            bm.getCloudletClassI_RTime().update(cloudletClassIRTime);
            //System.out.println("system response time classe 1 " + systemClassIRTime);
            bm.getSystemClassI_RTime().update(cloudletClassIRTime);

            bm.getCloudletThroughput_ClassI().update(completedN1/clock.getCurrent());
            //System.out.println(completedN1/clock.getCurrent());

            bm.getCloudletRTime().update(cloudletClassIRTime);

        } else {

            n2--;
            completedN2++;

            bm.getCloudletPopulation().update(n1 + n2);
            bm.getCloudletClassII_Population().update(n2);

            double cloudletClassIIRTime = event.getJob().getService_time();

            bm.getCloudletClassII_RTime().update(cloudletClassIIRTime);

            bm.getSystemClassII_RTime().update(cloudletClassIIRTime);

            bm.getCloudletThroughput_ClassII().update(completedN2/clock.getCurrent());
            //System.out.println(completedN2/clock.getCurrent());

            bm.getCloudletRTime().update(cloudletClassIIRTime);


        }

        double cloudletCompletion = completedN1 + completedN2;
        double cloudCompletion = Cloud.getCompletedN1() + Cloud.getCompletedN2();
        double completed = cloudCompletion + cloudletCompletion;

        bm.getSystemThroughput().update(completed/clock.getCurrent());
        bm.getSystemThroughput_ClassI().update((completedN1 + Cloud.getCompletedN1())/clock.getCurrent());
        bm.getSystemThroughput_ClassII().update((completedN2 + Cloud.getCompletedN2())/clock.getCurrent());

        double systemRTime = event.getJob().getService_time();

        //System.out.println("system response time " + systemRTime);
        bm.getSystemRTime().update(systemRTime);

        bm.getCloudletThroughput().update((completedN2 + completedN1)/ clock.getCurrent());
        //System.out.println((completedN2 + completedN1)/ clock.getCurrent());

    }

    public void updateStatistics() {
        if (n1 > 0) {                               /* update integrals  */
            areaN1.node += (clock.getCurrent() - clock.getPrevious()) * n1;
            areaN1.service += (clock.getCurrent() - clock.getPrevious());
        }

        if (n2 > 0) {                               /* update integrals  */
            areaN2.node += (clock.getCurrent() - clock.getPrevious()) * n2;
            areaN2.service += clock.getCurrent() - clock.getPrevious();
        }

        if (n1 + n2 > 0) {                               /* update integrals  */
            areaTot.node += (clock.getCurrent() - clock.getPrevious()) * (n1 + n2);
            areaTot.service += clock.getCurrent() - clock.getPrevious();
        }

    }

    public void updateCloudletStatistics() {

        Statistics stat = Statistics.getMe();
        stat.updateCloudletStatistics(n1,n2,totalN1,totalN2,completedN1,completedN2);

    }

    private void sendToTheCloud(Job job) {
        //TODO check if correct
        //save original arrival and set the new one (Shouldn't be the same?)
        job.setFirstarrival(job.getArrival());
        job.setArrival(clock.getCurrent()); // not sure if useless

        // System.out.println("old service time is: " + job.getService_time());
        //  job.printAll();
        if (job.getClasse()==1) {
            job.setService_time(r.streamExponential(1 / MU1cloud, 6));
        //     System.out.println("Job di classe 1 al cloud");
        }
        else {
       //     System.out.println("Job di classe 2 al cloud");
            job.setService_time(r.streamExponential(1 / MU2cloud, 7));
        }

        job.setSetup_time(r.streamExponential(0.8,8));
        job.setService_time(job.getService_time() + job.getSetup_time());

        job.setCompletion(job.getArrival() + job.getService_time());

       // System.out.println("new service time is: " +job.getService_time());
       // System.out.println("***********************************************************************************************");

        Event e = new CloudArrivalEvent(job,clock.getCurrent());
        eventList.pushEvent(eventList.getCloudEventList(), e);

    }

    public void printStatistics() {

        DecimalFormat f = new DecimalFormat("###0.00");

        int total = totalN1 + totalN2;

        System.out.println("\n job totali:  " + totalN1 + " & " + totalN2 );
        System.out.println("\n job totali:  " + total );
        System.out.println("job class 1 completati " + completedN1);
        System.out.println("job class 2 completati " + completedN2);

        System.out.println("   average interarrival time  class 1=   " + f.format(clock.getLast() / totalN1));
        System.out.println("   average interarrival time  class 2=   " + f.format(clock.getLast() / totalN2));
        System.out.println("   average interarrival time  tot=       " + f.format(clock.getLast() / (totalN1 + totalN2)));
//
//        System.out.println("   average wait class1............ =   " + f.format(areaN1.node / completedN1));
//        System.out.println("   average wait class2............ =   " + f.format(areaN2.node / completedN2));
//        System.out.println("   average wait tot............ =      " + f.format(areaTot.node / (completedN1 + completedN2)));
//
        System.out.println("   average service time class 1 .... =   " + f.format(areaN1.service / completedN1));
        System.out.println("   average service time class 2.... =    " + f.format(areaN2.service / completedN2));
        System.out.println("   average service time tot.... =        " + f.format(areaTot.service / (completedN1 + completedN2)));
//
        System.out.println("   average # in the node ... =   " + f.format(areaN1.node / clock.getCurrent()));
        System.out.println("   average # in the node ... =   " + f.format(areaN2.node / clock.getCurrent()));
        System.out.println("   average # in the node ... =   " + f.format(areaTot.node / clock.getCurrent()));
//
        System.out.println("   utilization ............. =   " + f.format(areaN1.service / clock.getCurrent()));
        System.out.println("   utilization ............. =   " + f.format(areaN2.service / clock.getCurrent()));
        System.out.println("   utilization ............. =   " + f.format(areaTot.service / clock.getCurrent()));
//
    }

    public static int getCompletedN1() {
        return completedN1;
    }

    public static int getCompletedN2() {
        return completedN2;
    }
}
