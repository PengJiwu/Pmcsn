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
    static double STOP = 20000.0;          /* terminal (close the door) time */
    static double INFINITY = 100.0 * STOP;  /* must be much larger than STOP  */
    static double LAMBDA1 = 6;
    static double MU1clet = 0.45;
    static double LAMBDA2 = 6.25;
    static double MU2clet = 0.27;
    static int N = 7;
    static int S = 3;

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


        Simulation simulation = new Simulation();
        simulation.run();


    }


    public Simulation()
    {
        area1 = new MmccArea();
        area2 = new MmccArea();
        areaTot = new MmccArea();
        area1.initAreaParas();
        area2.initAreaParas();
        areaTot.initAreaParas();


        r = new Rngs();
        r.plantSeeds(123456789);
        rvgs = new Rvgs(r);

        clock = Clock.getClock();

        eventList = EventList.getEventList();

        totalN1 = 0;
        totalN2 = 0;

        cloudlet = new Cloudlet(N,S);
        cloud = new Cloud();
    }

    private void run()
    {

        boolean stopArrivals = false;
        Event newEvent;

        while (clock.getCurrent()<STOP || !eventList.isEmpty()) {

            // GENERAZIONE NUOVO ARRIVO
            if (clock.getCurrent() > STOP) {
                clock.setLast(clock.getCurrent());
                stopArrivals = true;
            }

            if(!stopArrivals) {
                newEvent = createNewArrivalEvent();
            }

            Event e = eventList.popEvent();
            clock.setCurrent(e.getTimeOfEvent());



            if(e instanceof CloudletArrivalEvent)
            {
                cloudlet.processArrival((CloudletArrivalEvent)e);
            }

            if(e instanceof CloudletCompletionEvent){

            }

            if(e instanceof CloudCompletionEvent)
            {

            }

            if(e instanceof CloudArrivalEvent)
            {

            }
//
            System.out.println("******************************************************************************************");
            System.out.println("CURRENT: " + clock.getCurrent());
            eventList.printList();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println("******************************************************************************************");



        }

        /*
        //1) Genera arrivo

        //While(c'è un evento)

            pop evento dalla event list
            gestisci evento
            NB gestisci evento probabile che genera nuovi eventi (un arrivo genera un evento di completamento, un arrivo può generare un arrivo in cloud)
            aggiorna eventlist
            stima dei parametri per classe/per coda/ globali/ ecc

            scorri il Clock




        */
    }

    private Event createNewArrivalEvent() {
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
        eventList.pushEvent(newEvent);

        return  newEvent;
    }
}
