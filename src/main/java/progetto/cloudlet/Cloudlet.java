package progetto.cloudlet;

import progetto.Job;
import progetto.Statistics.BatchMeansStatistics;
import progetto.cloud.Cloud;
import progetto.events.*;
import rng.Rvgs;

/**
 * This class simulates Cloudlet's work
 */

public class Cloudlet {

    private int  n1, n2;
    private int N, S;
    private int totalN1, totalN2;
    private int arrivalN2;

    private    EventList eventList;

    static private int completedN1;
    static private int completedN2;
    private int interruptedJobs;
    private int blockedJobsN1;
    private int blockedJobsN2;
    private int class1toCloud;

    private Clock clock;

    private Rvgs r; // Needed to create an exponential setup time
    private double MU1clet = 0.45;
    private double MU2clet = 0.27;


    /**
     * This method inizializes variables
     * @param N
     * @param S
     * @param rvgs
     */

    public Cloudlet(int N, int S, Rvgs rvgs){

        n1 = 0;
        n2 = 0;

        completedN1 = 0;
        completedN2 = 0;

        interruptedJobs = 0;
        blockedJobsN1 = 0;
        blockedJobsN2 = 0;
        class1toCloud = 0;

        totalN1 = 0;
        totalN2 = 0;

        this.N = N;
        this.S = S;
        this.r = rvgs;

        eventList = EventList.getEventList();

        clock = Clock.getClock();
    }

    /**
     * This method dispatches arrivals according to their class
     * @param event
     */

    public void dispatchArrival(CloudletArrivalEvent event) {

//        BatchMeansStatistics bm = BatchMeansStatistics.getMe();

        Job nextArrivalJob = event.getJob();

        if (nextArrivalJob.getJobClass() == 1) {         // Class 1 job arrival

            totalN1++;

            if (n1 == N) {                              //Cloudlet is completely full

                sendToTheCloud(nextArrivalJob);

                blockedJobsN1++;
                class1toCloud++;

            }
            else if (n1 + n2 < S) {                     // Threshold hasn't been trespassed yet

                n1++;
                processArrival(nextArrivalJob);

            }
            else if (n2 > 0) {                         // Threshold has been passed and there's at least one class 2 job

                n1++;
                n2--;
                interruptedJobs++;
                Job job = eventList.removeOneC2CompletionEvent();
                job.setPrelation(true);

                sendToTheCloud(job);
                processArrival(nextArrivalJob);
            }
            else {                                     // Threshold has been passed and there aren't class 2 jobs

                n1++;
                processArrival(nextArrivalJob);
            }

        }

        else {                                        // Class 2 job arrival

            totalN2++;

            if (n1 + n2 >= S) {                      // Threshold has been passed

                sendToTheCloud(nextArrivalJob);
                blockedJobsN2++;
            }
            else {

                n2++;
                arrivalN2++;
                processArrival(nextArrivalJob);
            }
        }


        updateCompletionStatistics(null);


    }


    /**
     * This method takes in input an arrival and processes it
     * @param nextArrivalJob
     */

    private void processArrival(Job nextArrivalJob) {

        calcolateServiceTime(nextArrivalJob);
        createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());

    }

    /**
     * This method takes in input a job and calculates its service time
     * @param job
     */

    private void calcolateServiceTime(Job job) {

        if (job.getJobClass() == 1)                 // Class 1 job
            job.setService_time(r.streamExponential((1 / MU1clet), 4));

        else                                        // Class 2 job
            job.setService_time(r.streamExponential(1 / MU2clet, 5));

        job.setCompletion(job.getArrival() + job.getService_time());

    }

    /**
     * This method takes in input a job and creates a completion at timeOfEvent indicated
     * @param job
     * @param timeOfEvent
     */

    private void createNewCompletionEvent(Job job, double timeOfEvent) {

        Event e = new CloudletCompletionEvent(job,timeOfEvent);

        eventList.pushEvent(e);

    }

    /**
     * This method takes in input a Cloudlet completion event and processes it
     * @param event
     */

    public void processCompletion(CloudletCompletionEvent event) {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        if (event.getJob().getJobClass() == 1) {                        // Class 1 job

            n1--;
            completedN1++;

        }
        else {                                                          // Class 2 job
            n2--;
            completedN2++;
        }
        updateCompletionStatistics(event.getJob());

    }

    /**
     * This method takes in input a job and sends it to the Cloud
     * @param job
     */

    private void sendToTheCloud(Job job) {

        // Save original arrival and set the new one
        job.setFirstarrival(job.getArrival());
        job.setArrival(clock.getCurrent());

        Event e = new CloudArrivalEvent(job, clock.getCurrent());

        eventList.pushEvent(e);

    }

    public void updateCompletionStatistics(Job job)
    {
        //Update population statistics
        BatchMeansStatistics bm = BatchMeansStatistics.getMe();
        bm.getCloudletPopulation().update(n1 + n2);
        bm.getCloudletPopulation_ClassI().update(n1);
        bm.getCloudletPopulation_ClassII().update(n2);
        bm.getSentToTheCloudJobs().update((double)(blockedJobsN1 + blockedJobsN2 + interruptedJobs)/(totalN1 + totalN2));
        bm.getSentToTheCloudJobs_Class1().update((double)(blockedJobsN1)/(totalN1));

        if(totalN2 != 0) {
            bm.getSentToTheCloudJobs_Class2().update((double)(blockedJobsN2)/(totalN2));
            bm.getInterruptedTasksPercentage_ClassII().update((double) interruptedJobs / (totalN2));

        }
        //Update Response Time Statitistics
        if(job!= null) {
            if (job.getJobClass() == 1) {
                bm.getCloudletRTime_ClassI().update(job.getService_time());
                bm.getSystemRTime_ClassI().update(job.getService_time());
            } else {
                bm.getCloudletRTime_ClassII().update(job.getService_time());
                bm.getSystemRTime_ClassII().update(job.getService_time());
            }

            bm.getCloudletRTime().update(job.getService_time());
            bm.getSystemRTime().update(job.getService_time());
        }
        bm.getCloudletThroughput_ClassII().update(completedN2/clock.getCurrent());
        bm.getCloudletThroughput_ClassI().update(completedN1 / clock.getCurrent());


        //Update Throughtput statistics
        double cloudletCompletion = completedN1 + completedN2;
        double cloudCompletion = Cloud.getCompletedN1() + Cloud.getCompletedN2();
        double completed = cloudCompletion + cloudletCompletion;

        // Update statistics
        bm.getSystemThroughput().update(completed/clock.getCurrent());
        bm.getSystemThroughput_ClassI().update((double)(completedN1 + Cloud.getCompletedN1())/clock.getCurrent());
        bm.getSystemThroughput_ClassII().update((completedN2 + Cloud.getCompletedN2())/clock.getCurrent());
        double thr = (double) (completedN2 + completedN1)/ clock.getCurrent();
        bm.getCloudletThroughput().update(thr);



    }

    public static int getCompletedN1() {
        return completedN1;
    }

    public static int getCompletedN2() {
        return completedN2;
    }
}


