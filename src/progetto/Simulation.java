package progetto;



/*      TODO LIST:
*
*       Lista di eventi <- Interfaccia/classe astrata evento -> ArrivoCloudlet - ArrivoCloud - ComplCloudlet - ComplCloud
*       Clock che scorre in Simulation
*       Gestione degli eventi
*
*
* */

import progetto.Statistics.Statistics;
import progetto.Statistics.WelfordMean;
import progetto.cloud.Cloud;
import progetto.cloudlet.Cloudlet;
import progetto.events.*;
import rng.Rngs;
import rng.Rvgs;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import configuration.configuration;

public class Simulation {

    static int N;
    static int S;
    static int seed;
    static double START = 0.0;              /* initial time                   */
    static double STOP;          /* terminal (close the door) time */

    static double INFINITY = 100.0 * STOP;  /* must be much larger than STOP  */
    static double LAMBDA1 = 6;
    static double MU1clet = 0.45;
    static double LAMBDA2 = 6.25;
    static double MU2clet = 0.27;

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
    static configuration conf;

    public static void main(String[] args) throws Exception {

        conf = new configuration();
        conf.loadFIle();
        STOP = conf.duration;
        N = conf.N;
        S = conf.S;
        seed = conf.seed;


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

    private void run() throws FileNotFoundException, UnsupportedEncodingException {
        boolean stopArrivals = false;

        int i = 0;

        WelfordMean wf = new WelfordMean();

        createNewArrivalEvent();
        int j = 0;
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

                j++;
                if (j ==4)
                    break;
                cloudlet.processCompletion((CloudletCompletionEvent)e);
                //cloudlet.updateStatistics();
                cloud.updateCloudStatistics();
                cloudlet.updateCloudletStatistics();

                double elem = e.getJob().getService_time();
                System.out.println(" Service time is" + elem);
                wf.addElement(elem);

//                try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }



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

        double meantscloudlet= wf.getMean();
        double varianceTScloudlet = wf.getVariance();
        double standardDVTScloudlet = Math.sqrt(varianceTScloudlet);

        System.out.println("media              = " + meantscloudlet);
        System.out.println("varianza           = " + varianceTScloudlet);
        System.out.println("standard deviation = " + standardDVTScloudlet);

        //st.createFile();

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
