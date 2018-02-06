package progetto.cloudlet;


import progetto.Charts.*;
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

    int arrivalN2;


    EventList eventList;
    static int completedN1;
    static int completedN2;
    int interruptedJobs;
    int blockedJobs;
    int class1toCloud;

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
        blockedJobs = 0;
        class1toCloud = 0;

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

        BatchMeansStatistics bm = BatchMeansStatistics.getMe();

        Job nextArrivalJob = event.getJob();
        if (nextArrivalJob.getJobClass() == 1) {

            //Arriva job di classe 1
            totalN1++;

            if (n1 == N)    {                      //Cloudlet is completely full
                sendToTheCloud(nextArrivalJob);
                blockedJobs++;
                class1toCloud++;
                bm.getSentToTheCloudJobs().update((double)(blockedJobs + interruptedJobs)/(totalN1 + totalN2));
                            }


            else if (n1 + n2 < S) {               // Threshold hasn't been trespassed yet

                n1++;

                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletPopulation_ClassI().update(n1);

                createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());

            } else if (n2 > 0) {                    // Threshold has been passed and there's at least one class 2 job

                n1++;
                n2--;
                interruptedJobs++;

                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletPopulation_ClassI().update(n1);
                bm.getCloudletPopulation_ClassII().update(n2);
                bm.getSentToTheCloudJobs().update((double)(blockedJobs + interruptedJobs)/(totalN1 + totalN2));

                Job job = eventList.removeOneC2CompletionEvent();



                job.setPrelation(true);
                sendToTheCloud(job);
                createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());

                bm.getInterruptedTasksPercentage_ClassII().update((double) interruptedJobs/(totalN2));

            } else {                                // Threshold has been passed and there aren't class 2 jobs

                n1++;

                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletPopulation_ClassI().update(n1);

                createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
            }
        }
        else {

            totalN2++;
            //Arriva job di classe 2

            if (n1 + n2 >= S) {
                sendToTheCloud(nextArrivalJob);
                blockedJobs++;
                bm.getSentToTheCloudJobs().update((double)(blockedJobs + interruptedJobs)/(totalN1 + totalN2));
            } else {

                n2++;
                arrivalN2++;

                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletPopulation_ClassII().update(n2);

                createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
            }
        }
    }

    private void createNewCompletionEvent(Job job, double timeOfEvent) {

        Event e = new CloudletCompletionEvent(job,timeOfEvent);

        eventList.pushEvent(e);
    }

    public void processCompletion(CloudletCompletionEvent event) {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        if (event.getJob().getJobClass() == 1) {

            n1--;
            completedN1++;

            bm.getCloudletPopulation().update(n1 + n2);
            bm.getCloudletPopulation_ClassI().update(n1);

            double cloudletClassIRTime = event.getJob().getService_time();

            bm.getCloudletRTime_ClassI().update(cloudletClassIRTime);
            bm.getSystemRTime_ClassI().update(cloudletClassIRTime);

            N1RTCharts.getN1JobChart().addCoordinates(clock.getCurrent(),cloudletClassIRTime);

            bm.getCloudletThroughput_ClassI().update(completedN1/clock.getCurrent());

            bm.getCloudletRTime().update(cloudletClassIRTime);

        } else {

            n2--;
            completedN2++;

            bm.getCloudletPopulation().update(n1 + n2);
            bm.getCloudletPopulation_ClassII().update(n2);

            double cloudletClassIIRTime = event.getJob().getService_time();

            bm.getCloudletRTime_ClassII().update(cloudletClassIIRTime);

            bm.getSystemRTime_ClassII().update(cloudletClassIIRTime);

            N2RTCharts.getN2JobChart().addCoordinates(clock.getCurrent(),cloudletClassIIRTime);

            bm.getCloudletThroughput_ClassII().update(completedN2/clock.getCurrent());
            bm.getCloudletRTime().update(cloudletClassIIRTime);


        }

        double cloudletCompletion = completedN1 + completedN2;
        double cloudCompletion = Cloud.getCompletedN1() + Cloud.getCompletedN2();
        double completed = cloudCompletion + cloudletCompletion;

        bm.getSystemThroughput().update(completed/clock.getCurrent());
        bm.getSystemThroughput_ClassI().update((double)(completedN1 + Cloud.getCompletedN1())/clock.getCurrent());
        bm.getSystemThroughput_ClassII().update((completedN2 + Cloud.getCompletedN2())/clock.getCurrent());

        double systemRTime = event.getJob().getService_time();
        bm.getSystemRTime().update(systemRTime);

        RTCharts.getRTCharts().addCoordinates(clock.getCurrent(),systemRTime);

        double boh = (double) (completedN2 + completedN1)/ clock.getCurrent();
        bm.getCloudletThroughput().update(boh);
        ThroughputChart.getThroughputChart().addCoordinates(clock.getCurrent(),boh);

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

        if (n1 > 0) {                               /* update integrals  */
            areaN1.service += (clock.getCurrent() - clock.getPrevious());
            N1JobChart.getN1JobChart().addCoordinates(areaN1.service,n1);
        }
        if (n2 > 0) {                               /* update integrals  */
            areaN2.service += (clock.getCurrent() - clock.getPrevious());
            N2JobChart.getN2JobChart().addCoordinates(areaN2.service,n2);
        }


    }

    private void sendToTheCloud(Job job) {
        //save original arrival and set the new one (Shouldn't be the same?)
        job.setFirstarrival(job.getArrival());
        job.setArrival(clock.getCurrent());

        if (job.getJobClass()==1) {
            job.setService_time(r.streamExponential((1 / MU1cloud), 6));
        }
        else {
            job.setService_time(r.streamExponential(1 / MU2cloud, 7));
            if (job.isPrelated()){
                job.setSetup_time(r.streamExponential(0.8,8));
                job.setService_time(job.getService_time() + job.getSetup_time());
            }
        }
        job.setCompletion(job.getArrival() + job.getService_time());

        Event e = new CloudArrivalEvent(job,clock.getCurrent());

        eventList.pushEvent(e);

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


