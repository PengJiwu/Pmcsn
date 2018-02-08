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

    int n1, n2;
    int N, S;
    int totalN1, totalN2;
    int arrivalN2;

    EventList eventList;

    static int completedN1;
    static int completedN2;
    int interruptedJobs;
    int blockedJobs;
    int class1toCloud;

    Clock clock;

    Rvgs r; // Needed to create an exponential setup time
    static double MU1clet = 0.45;
    static double MU2clet = 0.27;

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
        blockedJobs = 0;
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

        BatchMeansStatistics bm = BatchMeansStatistics.getMe();

        Job nextArrivalJob = event.getJob();

        if (nextArrivalJob.getJobClass() == 1) {         // Class 1 job arrival

            totalN1++;

            if (n1 == N) {                              //Cloudlet is completely full

                sendToTheCloud(nextArrivalJob);

                blockedJobs++;
                class1toCloud++;

                bm.getSentToTheCloudJobs().update((double)(blockedJobs + interruptedJobs)/(totalN1 + totalN2));

            }
            else if (n1 + n2 < S) {                     // Threshold hasn't been trespassed yet

                n1++;

                // Updates statistics
                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletPopulation_ClassI().update(n1);
                processArrival(nextArrivalJob);

            }
            else if (n2 > 0) {                         // Threshold has been passed and there's at least one class 2 job

                n1++;
                n2--;
                interruptedJobs++;

                // Updates statistics
                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletPopulation_ClassI().update(n1);
                bm.getCloudletPopulation_ClassII().update(n2);
                bm.getSentToTheCloudJobs().update((double)(blockedJobs + interruptedJobs)/(totalN1 + totalN2));

                Job job = eventList.removeOneC2CompletionEvent();
                job.setPrelation(true);

                sendToTheCloud(job);
                processArrival(nextArrivalJob);
                bm.getInterruptedTasksPercentage_ClassII().update((double) interruptedJobs/(totalN2));

            }
            else {                                     // Threshold has been passed and there aren't class 2 jobs

                n1++;

                // Updates statistics
                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletPopulation_ClassI().update(n1);
                processArrival(nextArrivalJob);
            }

        }

        else {                                        // Class 2 job arrival

            totalN2++;

            if (n1 + n2 >= S) {                      // Threshold has been passed

                sendToTheCloud(nextArrivalJob);
                blockedJobs++;

                bm.getSentToTheCloudJobs().update((double)(blockedJobs + interruptedJobs)/(totalN1 + totalN2));
            }
            else {

                n2++;
                arrivalN2++;

                // Update statistics
                bm.getCloudletPopulation().update(n1 + n2);
                bm.getCloudletPopulation_ClassII().update(n2);

                processArrival(nextArrivalJob);
            }
        }
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

            double cloudletClassIRTime = event.getJob().getService_time();

            // Update statistics
            bm.getCloudletPopulation().update(n1 + n2);
            bm.getCloudletPopulation_ClassI().update(n1);

            bm.getCloudletRTime().update(cloudletClassIRTime);
            bm.getCloudletRTime_ClassI().update(cloudletClassIRTime);

            bm.getCloudletThroughput_ClassI().update(completedN1/clock.getCurrent());

            bm.getSystemRTime_ClassI().update(cloudletClassIRTime);

        }
        else {                                                          // Class 2 job

            n2--;
            completedN2++;

            double cloudletClassIIRTime = event.getJob().getService_time();

            // Update statistics
            bm.getCloudletPopulation().update(n1 + n2);
            bm.getCloudletPopulation_ClassII().update(n2);

            bm.getCloudletRTime().update(cloudletClassIIRTime);
            bm.getCloudletRTime_ClassII().update(cloudletClassIIRTime);

            bm.getCloudletThroughput_ClassII().update(completedN2/clock.getCurrent());

            bm.getSystemRTime_ClassII().update(cloudletClassIIRTime);

        }

        double cloudletCompletion = completedN1 + completedN2;
        double cloudCompletion = Cloud.getCompletedN1() + Cloud.getCompletedN2();
        double completed = cloudCompletion + cloudletCompletion;

        // Update statistics
        bm.getSystemThroughput().update(completed/clock.getCurrent());
        bm.getSystemThroughput_ClassI().update((double)(completedN1 + Cloud.getCompletedN1())/clock.getCurrent());
        bm.getSystemThroughput_ClassII().update((completedN2 + Cloud.getCompletedN2())/clock.getCurrent());

        double systemRTime = event.getJob().getService_time();
        bm.getSystemRTime().update(systemRTime);


        double thr = (double) (completedN2 + completedN1)/ clock.getCurrent();
        bm.getCloudletThroughput().update(thr);

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

    public static int getCompletedN1() {
        return completedN1;
    }

    public static int getCompletedN2() {
        return completedN2;
    }
}


