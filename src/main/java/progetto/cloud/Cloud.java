package progetto.cloud;

import progetto.Charts.N1RTCharts;
import progetto.Charts.N2RTCharts;
import progetto.Charts.RTCharts;
import progetto.Job;
import progetto.MmccArea;
import progetto.Statistics.BatchMeans;
import progetto.Statistics.BatchMeansStatistics;
import progetto.Statistics.Statistics;
import progetto.cloudlet.Cloudlet;
import progetto.events.*;
import rng.Rvgs;

public class Cloud {

    int n1,n2;

    EventList eventList;
    static int completedN1;
    static int completedN2;

    MmccArea areaN1;
    MmccArea areaN2;
    MmccArea areaTot;
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

        areaN1 = new MmccArea();
        areaN2 = new MmccArea();
        areaTot = new MmccArea();
        areaN1.initAreaParas();
        areaN2.initAreaParas();
        areaTot.initAreaParas();

        clock = Clock.getClock();
    }


    public void processArrival(CloudArrivalEvent event)  {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        Job nextArrivalJob = event.getJob();
        if (nextArrivalJob.getJobClass() ==1) {

            n1++;

            bm.getCloudPopulation_ClassI().update(n1);
            bm.getCloudPopulation().update(n1 + n2);

        }

        else {

            n2++;

            bm.getCloudPopulation_ClassII().update(n2);
            bm.getCloudPopulation().update(n1 + n2);

        }
        calcolateServiceTime(nextArrivalJob);
        createNewCompletionEvent(nextArrivalJob, nextArrivalJob.getCompletion());

    }

    private void calcolateServiceTime(Job job)
    {
        if (job.getJobClass()==1) {
            job.setService_time(r.streamExponential(1 / MU1cloud, 6));
        }
        else {
            job.setService_time(r.streamExponential(1 / MU2cloud, 7));
            if (job.isPrelated()){
                job.setSetup_time(r.streamExponential(0.8,8));
                job.setService_time(job.getService_time() + job.getSetup_time());
            }
        }
        job.setCompletion(job.getArrival() + job.getService_time());

    }

    private void createNewCompletionEvent(Job nextArrivalJob, double completion) {

        Event e = new CloudCompletionEvent(nextArrivalJob, completion);

        eventList.pushEvent(e);

    }

    public void processCompletion(CloudCompletionEvent e) {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        Job completedJob = e.getJob();

        if (completedJob.getJobClass() ==1) {

            n1--;
            completedN1++;

            bm.getCloudPopulation_ClassI().update(n1);
            bm.getCloudPopulation().update(n1 + n2);

            double cloudClassIRTime = completedJob.getCompletion() - completedJob.getFirstarrival();




            N1RTCharts.getN1JobChart().addCoordinates(clock.getCurrent(),cloudClassIRTime);

            bm.getCloudRTime_ClassI().update(completedJob.getService_time());
            bm.getSystemRTime_ClassI().update(cloudClassIRTime);

            bm.getCloudRTime().update(completedJob.getService_time());


        }
        else {

            n2--;
            completedN2++;

            bm.getCloudPopulation_ClassII().update(n2);
            bm.getCloudPopulation().update(n1 + n2);

            double cloudClassIIRTime = completedJob.getCompletion() - completedJob.getFirstarrival();
            bm.getCloudRTime_ClassII().update(completedJob.getService_time());
            bm.getSystemRTime_ClassII().update(cloudClassIIRTime);



            N2RTCharts.getN2JobChart().addCoordinates(clock.getCurrent(),cloudClassIIRTime);
            bm.getCloudRTime().update(completedJob.getService_time());


            if (completedJob.isPrelated())
                bm.getInterruptedTasksRTime_ClassII().update(completedJob.getCompletion() - completedJob.getFirstarrival());

        }

        double systemRTime = completedJob.getCompletion() - completedJob.getFirstarrival();
        bm.getSystemRTime().update(systemRTime);
        RTCharts.getRTCharts().addCoordinates(clock.getCurrent(),systemRTime);


        double cloudCompletion = completedN1 + completedN2;
        double cloudletCompletion = Cloudlet.getCompletedN1() + Cloudlet.getCompletedN2();
        double completed = cloudCompletion + cloudletCompletion;

        bm.getSystemThroughput().update((double) completed/clock.getCurrent());
        bm.getSystemThroughput_ClassI().update(((double) (completedN1 + Cloudlet.getCompletedN1())/clock.getCurrent()));
        bm.getSystemThroughput_ClassII().update(((double)(completedN2 + Cloudlet.getCompletedN2())/clock.getCurrent()));

    }

    public void updateCloudStatistics() {

        Statistics stat = Statistics.getMe();
        stat.updateCloudStatistics(n1,n2,completedN1,completedN2);

    }

    public static int getCompletedN1() {
        return completedN1;
    }

    public static int getCompletedN2() {
        return completedN2;
    }
}
