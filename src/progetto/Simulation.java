package progetto;



/*      TODO LIST:
*
*       Lista di eventi <- Interfaccia/classe astrata evento -> ArrivoCloudlet - ArrivoCloud - ComplCloudlet - ComplCloud
*       Clock che scorre in Simulation
*       Gestione degli eventi
*
*
* */

import progetto.cloud.Cloud;
import progetto.cloudlet.Cloudlet;
import progetto.events.*;
import rng.Rngs;
import rng.Rvgs;

public class Simulation {


    static double START = 0.0;              /* initial time                   */
    static double STOP = 20000;          /* terminal (close the door) time */
    static double INFINITY = 100.0 * STOP;  /* must be much larger than STOP  */
    static double LAMBDA1 = 6;
    static double MU1clet = 0.45;
    static double LAMBDA2 = 6.25;
    static double MU2clet = 0.27;
    static int N = 20;
    static int S = 20;

    MmccArea area1;
    MmccArea area2;
    MmccArea areaTot;

    Rngs r;
    Rvgs rvgs;

    Clock clock;

    EventList eventList;
    int totalN1,totalN2;

    Cloudlet cloudlet;
    Cloud cloud;

    public static void main(String[] args) {

        SimulationTimer st = new SimulationTimer();
        st.startTimer();

        Simulation simulation = new Simulation();
        simulation.run();

        st.stopTimer();
        st.showTimer();
        double secStop = STOP/1000;
        System.out.println("For a " + secStop + " seconds long simulation");

    }


    public Simulation()
    {



        r = new Rngs();
        r.plantSeeds(123456789);
        rvgs = new Rvgs(r);

        clock = Clock.getClock();

        eventList = EventList.getEventList();

        totalN1 = 0;
        totalN2 = 0;

        cloudlet = new Cloudlet(N,S,rvgs);
        cloud = new Cloud();
    }

    private void run()
    {
        boolean stopArrivals = false;

        createNewArrivalEvent();
        int i = 0;
        while (clock.getCurrent()<STOP || !eventList.isEmpty(eventList.getCloudEventList()) || !eventList.isEmpty(eventList.getCloudletEventList())) {

            i++;

            if (clock.getCurrent() > STOP) {
                clock.setLast(clock.getCurrent());
                stopArrivals = true;
            }

//
//            System.out.println("*********************************************************************************************************************");
//            System.out.println("CTIME: " + clock.getCurrent());
////            eventList.printList();
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }

            clock.setPrevious(clock.getCurrent());
            Event e = eventList.popEvent();
            clock.setCurrent(e.getTimeOfEvent());

            if(e instanceof CloudletArrivalEvent)
            {
                cloudlet.processArrival((CloudletArrivalEvent)e);
              //  cloudlet.updateStatistics();
                cloud.updateCloudStatistics();
                cloudlet.updateCloudletStatistics();


                if(!stopArrivals) {
                    createNewArrivalEvent();
                }


            }
            else
            if(e instanceof CloudletCompletionEvent){

                cloudlet.processCompletion((CloudletCompletionEvent)e);
                //cloudlet.updateStatistics();
                cloud.updateCloudStatistics();
                cloudlet.updateCloudletStatistics();
            }
            else
            if(e instanceof CloudArrivalEvent)
            {

                cloud.processArrival((CloudArrivalEvent)e);
                //cloudlet.updateStatistics();
                cloudlet.updateCloudletStatistics();
                cloud.updateCloudStatistics();

            }
            else
            if(e instanceof CloudCompletionEvent)
            {

                cloud.processCompletion((CloudCompletionEvent)e);
              //  cloudlet.updateStatistics();
                cloudlet.updateCloudletStatistics();
                cloud.updateCloudStatistics();

            }

        }

        Statistics st = Statistics.getMe();
        st.printStatistics();

        //cloudlet.printStatistics();

        System.out.println("numero cicli: " + i);
    }

    private void createNewArrivalEvent() {
        Job newJob;
        if (rvgs.uniform(0, 1) > LAMBDA1 / (LAMBDA1 + LAMBDA2)) {
                    /*Il prossimo arrivo sarà job di classe 2 */
            newJob = new Job(clock.getCurrent(), rvgs, (LAMBDA1 + LAMBDA2), MU2clet, 2);
            totalN2++;
        } else {
                    /*Il prossimo arrivo sarà job di classe 1*/
            newJob = new Job(clock.getCurrent(), rvgs, (LAMBDA1 + LAMBDA2), MU1clet, 1);
            totalN1++;
        }

        Event newEvent = new CloudletArrivalEvent(newJob, newJob.getArrival());
        eventList.pushEvent(eventList.getCloudletEventList(),newEvent);
    }
}
