package progetto.cloud;

import progetto.Job;
import progetto.MmccArea;
import progetto.events.*;

public class Cloud {

    int n1,n2;

    EventList eventList;
    int completedN1, completedN2;

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



    public void processArrival(CloudArrivalEvent event)
    {



        Job nextArrivalJob = event.getJob();
        if (nextArrivalJob.getClasse() ==1)
            n1++;
        else
            n2++;

        createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());



    }

    private void createNewCompletionEvent(Job nextArrivalJob, double completion) {

        Event e = new CloudCompletionEvent(nextArrivalJob,completion);

        eventList.pushEvent(eventList.getCloudEventList(), e);

    }

    public void processCompletion(CloudCompletionEvent e) {

        Job nextArrivalJob = e.getJob();
        if (nextArrivalJob.getClasse() ==1)
        {   n1--;
            completedN1++;}
        else
            {
            n2--;
            completedN2++;}


    }

    public void updateCloudStatistics(){

        Statistics stat = Statistics.getMe();
        stat.updateCloudStatistics(n1,n2,completedN1,completedN2);

    }
}
