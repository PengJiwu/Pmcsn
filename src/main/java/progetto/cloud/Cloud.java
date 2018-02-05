package progetto.cloud;

import progetto.Job;
import progetto.MmccArea;
import progetto.Statistics.BatchMeans;
import progetto.Statistics.BatchMeansStatistics;
import progetto.Statistics.Statistics;
import progetto.cloudlet.Cloudlet;
import progetto.events.*;

public class Cloud {

    int n1,n2;

    EventList eventList;
    static int completedN1;
    static int completedN2;

    MmccArea areaN1;
    MmccArea areaN2;
    MmccArea areaTot;
    Clock clock;

    public Cloud(){

        n1 = 0;
        n2 = 0;
        completedN1=0;
        completedN2=0;

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
        if (nextArrivalJob.getClasse() ==1) {

            n1++;

            bm.getCloudClassI_Population().update(n1);
            bm.getCloudPopulation().update(n1 + n2);

        }

        else {

            n2++;

            bm.getCloudClassII_Population().update(n2);
            bm.getCloudPopulation().update(n1 + n2);

        }

        createNewCompletionEvent(nextArrivalJob, nextArrivalJob.getCompletion());

    }

    private void createNewCompletionEvent(Job nextArrivalJob, double completion) {

        Event e = new CloudCompletionEvent(nextArrivalJob, completion);

        eventList.pushEvent(eventList.getCloudEventList(), e);

    }

    public void processCompletion(CloudCompletionEvent e) {

        BatchMeansStatistics bm;
        bm = BatchMeansStatistics.getMe();

        Job nextArrivalJob = e.getJob();

        if (nextArrivalJob.getClasse() ==1) {

            n1--;
            completedN1++;

            bm.getCloudClassI_Population().update(n1);
            bm.getCloudPopulation().update(n1 + n2);

            double cloudClassIRTime = nextArrivalJob.getCompletion() - nextArrivalJob.getFirstarrival();

            bm.getCloudClassI_RTime().update(nextArrivalJob.getService_time());
            bm.getSystemClassI_RTime().update(cloudClassIRTime);

            bm.getCloudRTime().update(nextArrivalJob.getService_time());


        }
        else {

            n2--;
            completedN2++;

            bm.getCloudClassII_Population().update(n2);
            bm.getCloudPopulation().update(n1 + n2);

            double cloudClassIIRTime = nextArrivalJob.getCompletion() - nextArrivalJob.getFirstarrival();
            bm.getCloudClassII_RTime().update(nextArrivalJob.getService_time());
            bm.getSystemClassII_RTime().update(cloudClassIIRTime);

            bm.getCloudRTime().update(nextArrivalJob.getService_time());


            if (nextArrivalJob.getPrelation() == true)
                bm.getInterruptedTasks_classII_RTime().update(nextArrivalJob.getCompletion() - nextArrivalJob.getFirstarrival());

        }

        double systemRTime = nextArrivalJob.getCompletion() - nextArrivalJob.getFirstarrival();
        bm.getSystemRTime().update(systemRTime);

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
