package progetto.cloudlet;


import progetto.Job;
import progetto.Mmcc;
import progetto.events.*;

public class Cloudlet {


    int n1,n2;
    int N,S;
    EventList eventList;

    public Cloudlet(int N,int S){

        n1 = 0;
        n2 = 0;
        this.N = N;
        this.S = S;

        eventList = EventList.getEventList();


    }


    public void processArrival(CloudletArrivalEvent event)
    {

            Job nextArrivalJob = event.getJob();
            if (nextArrivalJob.getClasse() == 1) {
                //Arriva job di classe 1


                if (n1 == N) {

                    sendToTheCloud(nextArrivalJob);  //TODO
                } else if (n1 + n2 < S) {
                    n1++;
                    createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
                } else if (n2 > 0) {
//                    Job jobToSend = Mmcc.selectOneC2Job(jobList);
//                    jobList.remove(jobToSend);
//                    sendToTheCloud(jobToSend);
//                    n1++;
//                    n2--;
//                                        /*Caso limite in cui ad essere eliminato è il job successivo ad essere completato*/
//                    if (jobToSend.getCompletion() == t.completion)
//                    {
//                        nextCompletionJob = Mmcc.getMinCompletion(jobList);
//                        t.completion = nextCompletionJob.getCompletion();// t.completion = MINIMO DI TUTTI I tfine
//                    }
                    n1++;
                    n2--;
                    eventList.removeOneC2CompletionEvent();
                    createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
                } else {
                    n1++;
                    createNewCompletionEvent(nextArrivalJob,nextArrivalJob.getCompletion());
                }
            } else {
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

    public void processCompletion(CloudCompletionEvent event)
    {

    }


    private void sendToTheCloud(Job job)
    {

        return ;
    }

}
