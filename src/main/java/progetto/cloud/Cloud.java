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

    private static int n1,n2;

    private EventList eventList;
    private static int completedN1;
    private static int completedN2;

    private Clock clock;

    private Rvgs r;

    private double MU1cloud = 0.25;
    private double MU2cloud = 0.22;

    private static Cloud me ;

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
        }

        else {                                      // Arrives a class 2 job
            n2++;
        }

        calculateServiceTime(nextArrivalJob);
        createNewCompletionEvent(nextArrivalJob, nextArrivalJob.getCompletion());

        updateStatistics(null);
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
        Job completedJob = e.getJob();

        if (completedJob.getJobClass() ==1) {           // class 1 job completed

            n1--;
            completedN1++;
        }

        else {                                          // class 2 job completed

            n2--;
            completedN2++;
        }
        updateStatistics(completedJob);
    }

    public static int getCompletedN1() {
        return completedN1;
    }

    public static int getCompletedN2() {
        return completedN2;
    }

    public static int getN1() {
        return n1;
    }

    public static int getN2() {
        return n2;
    }

    private void updateStatistics(Job job)
    {
        BatchMeansStatistics bm = BatchMeansStatistics.getMe();


        //Update Population Statistics
        bm.getCloudPopulation_ClassI().update(n1);
        bm.getCloudPopulation_ClassII().update(n2);
        bm.getCloudPopulation().update(n1 + n2);

        bm.getSystemPopulation().update(n1 + n2 + Cloudlet.getN1() + Cloudlet.getN2());
        bm.getSystemPopulation_ClassI().update(n1 +Cloudlet.getN1());
        bm.getSystemPopulation_ClassII().update(n2 +Cloudlet.getN2());



        //Update Response Time Statistics
        if(job!= null)
        {
            if(job.getJobClass()==1)
            {
                double cloudClassIRTime = job.getCompletion() - job.getFirstarrival();
                bm.getCloudRTime_ClassI().update(job.getService_time());
                bm.getSystemRTime_ClassI().update(cloudClassIRTime);

            }
            else
            {
                double cloudClassIIRTime = job.getCompletion() - job.getFirstarrival();
                bm.getCloudRTime_ClassII().update(job.getService_time());
                bm.getSystemRTime_ClassII().update(cloudClassIIRTime);

                if (job.isPrelated()) {// class 2 job with prelation completed
                    bm.getInterruptedTasksRTime_ClassII().update(job.getCompletion() - job.getFirstarrival());
                    bm.getInterruptedTasksCloudRTime_ClassII().update(job.getCompletion() - job.getArrival());
                    bm.getInterruptedTasksCloudletRTime_ClassII().update(job.getArrival() - job.getFirstarrival());
                }
            }
            bm.getCloudRTime().update(job.getService_time());
            double systemRTime = job.getCompletion() - job.getFirstarrival();
            bm.getSystemRTime().update(systemRTime);
        }

        //Update Throughput Statistics
        double cloudCompletion = completedN1 + completedN2;
        double cloudletCompletion = Cloudlet.getCompletedN1() + Cloudlet.getCompletedN2();
        double completed = cloudCompletion + cloudletCompletion;

        // Update statistics
        bm.getSystemThroughput().update((double) completed/clock.getCurrent());
        bm.getSystemThroughput_ClassI().update(((double) (completedN1 + Cloudlet.getCompletedN1())/clock.getCurrent()));
        bm.getSystemThroughput_ClassII().update(((double)(completedN2 + Cloudlet.getCompletedN2())/clock.getCurrent()));


    }


}
