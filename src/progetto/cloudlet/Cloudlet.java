package progetto.cloudlet;


import progetto.Job;
import progetto.MmccArea;
import progetto.events.*;

import java.text.DecimalFormat;

public class Cloudlet {


    int n1,n2;
    int N,S;
    EventList eventList;
    int completedN1, completedN2;

    MmccArea areaN1;
    MmccArea areaN2;
    MmccArea areaTot;
    Clock clock;

    int totalN1,totalN2;

    public Cloudlet(int N,int S){

        n1 = 0;
        n2 = 0;
        completedN1=0;
        completedN2=0;
        this.N = N;
        this.S = S;
        eventList = EventList.getEventList();

        areaN1 = new MmccArea();
        areaN2 = new MmccArea();
        areaTot = new MmccArea();
        areaN1.initAreaParas();
        areaN2.initAreaParas();
        areaTot.initAreaParas();

        totalN1 = 0;
        totalN2 = 0;


        clock = Clock.getClock();
    }


    public void processArrival(CloudletArrivalEvent event)
    {

            Job nextArrivalJob = event.getJob();
            if (nextArrivalJob.getClasse() == 1) {
                //Arriva job di classe 1

                totalN1++;

                if (n1 == N) {

                    sendToTheCloud(nextArrivalJob);  //TODO
                } else if (n1 + n2 < S) {
                    n1++;
                    createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
                } else if (n2 > 0) {
                    n1++;
                    n2--;
                    eventList.removeOneC2CompletionEvent();
                    createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
                } else {
                    n1++;
                    createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
                }
            } else {
                totalN2++;
                //Arriva job di classe 2
                if (n1 + n2 >= S) {
                    sendToTheCloud(nextArrivalJob);
                } else {
                    n2++;
                    createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
                }
            }
    }

    private void createNewCompletionEvent(Job job, double timeOfEvent)
    {
        Event e = new CloudletCompletionEvent(job,timeOfEvent);

        eventList.pushEvent(e);
    }

    public void processCompletion(CloudletCompletionEvent event)
    {

        if (event.getJob().getClasse() == 1) {
            n1--;
            completedN1++;
        } else {
            n2--;
            completedN2++;
        }


    }

    public void updateStatistics()
    {

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

    private void sendToTheCloud(Job job)
    {
        return ;
    }

    public void printStatistics() {

        DecimalFormat f = new DecimalFormat("###0.00");

        System.out.println("\n job totali:  " + totalN1 + " & " + totalN2);
        System.out.println("job class 1 completati " + completedN1);
        System.out.println("job class 2 completati " + completedN2);

        System.out.println("   average interarrival time  class 1=   " + f.format(clock.getLast() / totalN1));
        System.out.println("   average interarrival time  class 2=   " + f.format(clock.getLast() / totalN2));
        System.out.println("   average interarrival time  tot=       " + f.format(clock.getLast() / (totalN1 + totalN2)));

        System.out.println("   average wait class1............ =   " + f.format(areaN1.node / completedN1));
        System.out.println("   average wait class2............ =   " + f.format(areaN2.node / completedN2));
        System.out.println("   average wait tot............ =      " + f.format(areaTot.node / (completedN1 + completedN2)));

        System.out.println("   average service time class 1 .... =   " + f.format(areaN1.service / completedN1));
        System.out.println("   average service time class 2.... =    " + f.format(areaN2.service / completedN2));
        System.out.println("   average service time tot.... =        " + f.format(areaTot.service / (completedN1 + completedN2)));

        System.out.println("   average # in the node ... =   " + f.format(areaN1.node / clock.getCurrent()));
        System.out.println("   average # in the node ... =   " + f.format(areaN2.node / clock.getCurrent()));
        System.out.println("   average # in the node ... =   " + f.format(areaTot.node / clock.getCurrent()));

        System.out.println("   utilization ............. =   " + f.format(areaN1.service / clock.getCurrent()));
        System.out.println("   utilization ............. =   " + f.format(areaN2.service / clock.getCurrent()));
        System.out.println("   utilization ............. =   " + f.format(areaTot.service / clock.getCurrent()));

    }
}
