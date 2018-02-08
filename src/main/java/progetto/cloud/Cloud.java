package progetto.cloud;

import progetto.Charts.N1RTCharts;
import progetto.Charts.N2RTCharts;
import progetto.Charts.RTCharts;
import progetto.Job;
import progetto.Statistics.BatchMeansStatistics;
import progetto.cloudlet.Cloudlet;
import progetto.events.*;
import rng.Rvgs;

/**
 * This is the class that simulates Cloud's work of our system
 */

public class Cloud {

    int n1,n2;

    EventList eventList;
    static int completedN1;
    static int completedN2;

    Clock clock;

    private Rvgs r;

    static double MU1cloud = 0.25;
    static double MU2cloud = 0.22;

    public Cloud(Rvgs rvgs){

        n1 = 0;
        n2 = 0;
        completedN1=0;
        completedN2=0;

        this.r = rvgs;

        eventList = EventList.getEventList();

        clock = Clock.getClock();
    }

    /**
     * This method process a new arrival to the Cloud
     * @param event
     */

    public void processArrival(CloudArrivalEvent event) {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        Job nextArrivalJob = event.getJob();
        if (nextArrivalJob.getJobClass() ==1) {     // Arrives a class 1 job

            n1++;

            bm.getCloudPopulation_ClassI().update(n1);
            bm.getCloudPopulation().update(n1 + n2);

        }

        else {                                      // Arrives a class 2 job

            n2++;

            bm.getCloudPopulation_ClassII().update(n2);
            bm.getCloudPopulation().update(n1 + n2);

        }

        calculateServiceTime(nextArrivalJob);
        createNewCompletionEvent(nextArrivalJob, nextArrivalJob.getCompletion());

    }

    /**
     * This method calculates job's new service time
     * @param job
     */

    private void calculateServiceTime(Job job) {

        if (job.getJobClass() ==1 )                                      // calculate service time for class 1 jobs
            job.setService_time(r.streamExponential(1 / MU1cloud, 6));

        else {                                                           // calculate service time for class 2 jobs
            job.setService_time(r.streamExponential(1 / MU2cloud, 7));

            if (job.isPrelated()){                                       // adds setup time if needed
                job.setSetup_time(r.streamExponential(0.8,8));
                job.setService_time(job.getService_time() + job.getSetup_time());
            }
        }

        job.setCompletion(job.getArrival() + job.getService_time());

    }

    /**
     * This method takes in input a job and generates his completion
     * Eventually adds it to the list of Cloud events
     * @param nextArrivalJob
     * @param completion
     */

    private void createNewCompletionEvent(Job nextArrivalJob, double completion) {

        Event e = new CloudCompletionEvent(nextArrivalJob, completion);
        eventList.pushEvent(e);

    }

    /**
     * This method takes in input a Cloud completion event and processes it
     * @param e
     */

    public void processCompletion(CloudCompletionEvent e) {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        Job completedJob = e.getJob();

        if (completedJob.getJobClass() ==1) {           // class 1 job completed

            n1--;
            completedN1++;

            double cloudClassIRTime = completedJob.getCompletion() - completedJob.getFirstarrival();

            // Update statistics
            bm.getCloudPopulation_ClassI().update(n1);
            bm.getCloudPopulation().update(n1 + n2);
            bm.getCloudRTime_ClassI().update(completedJob.getService_time());
            bm.getSystemRTime_ClassI().update(cloudClassIRTime);
            bm.getCloudRTime().update(completedJob.getService_time());

            N1RTCharts.getN1JobChart().addCoordinates(clock.getCurrent(),cloudClassIRTime);

        }

        else {                                          // class 2 job completed

            n2--;
            completedN2++;

            double cloudClassIIRTime = completedJob.getCompletion() - completedJob.getFirstarrival();

            // Update statistics
            bm.getCloudPopulation_ClassII().update(n2);
            bm.getCloudPopulation().update(n1 + n2);
            bm.getCloudRTime_ClassII().update(completedJob.getService_time());
            bm.getSystemRTime_ClassII().update(cloudClassIIRTime);

            N2RTCharts.getN2JobChart().addCoordinates(clock.getCurrent(),cloudClassIIRTime);
            bm.getCloudRTime().update(completedJob.getService_time());


            if (completedJob.isPrelated())              // class 2 job with prelation completed
                bm.getInterruptedTasksRTime_ClassII().update(completedJob.getCompletion() - completedJob.getFirstarrival());

        }

        double systemRTime = completedJob.getCompletion() - completedJob.getFirstarrival();
        bm.getSystemRTime().update(systemRTime);
        RTCharts.getRTCharts().addCoordinates(clock.getCurrent(),systemRTime);

        double cloudCompletion = completedN1 + completedN2;
        double cloudletCompletion = Cloudlet.getCompletedN1() + Cloudlet.getCompletedN2();
        double completed = cloudCompletion + cloudletCompletion;

        // Update statistics
        bm.getSystemThroughput().update((double) completed/clock.getCurrent());
        bm.getSystemThroughput_ClassI().update(((double) (completedN1 + Cloudlet.getCompletedN1())/clock.getCurrent()));
        bm.getSystemThroughput_ClassII().update(((double)(completedN2 + Cloudlet.getCompletedN2())/clock.getCurrent()));

    }

    public static int getCompletedN1() {
        return completedN1;
    }

    public static int getCompletedN2() {
        return completedN2;
    }

}
