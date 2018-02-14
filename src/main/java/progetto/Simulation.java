package progetto;

import configuration.Configuration;
import progetto.Statistics.BatchMeansStatistics;
import progetto.cloud.Cloud;
import progetto.cloudlet.Cloudlet;
import progetto.events.*;
import rng.Rngs;
import rng.Rvgs;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * This is the main core of the simulation
 */

public class Simulation {

    static int N;
    static int S;
    static int seed;
    static double STOP;          /* terminal (close the door) time */

    static double LAMBDA1 = 6;
    static double LAMBDA2 = 6.25;
    static Configuration conf;
    Rngs r;
    Rvgs rvgs;
    Clock clock;
    EventList eventList;
    int totalN1, totalN2;
    Cloudlet cloudlet;
    Cloud cloud;


    public Simulation() {

        r = new Rngs();
        r.plantSeeds(seed);
        rvgs = new Rvgs(r);
        clock = Clock.getClock();
        eventList = EventList.getEventList();
        totalN1 = 0;
        totalN2 = 0;
        cloudlet = new Cloudlet(N, S, rvgs);
        cloud = new Cloud(rvgs);

    }

    /**
     * This method starts simulation
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {

        conf = Configuration.getConfiguration();
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
        double secStop = STOP / 1000;
        System.out.println("For a " + secStop + " seconds long simulation");

    }

    /**
     * This method runs the simulation
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */

    public void run() throws FileNotFoundException, UnsupportedEncodingException {

        boolean stopArrivals = false;
        createNewArrivalEvent();
        while (clock.getCurrent() < STOP || !eventList.isEmpty(eventList.getCloudEventList()) ||
                !eventList.isEmpty(eventList.getCloudletEventList())) {

            if (clock.getCurrent() > STOP) {
                clock.setLast(clock.getCurrent());
                stopArrivals = true;
            }

            clock.setPrevious(clock.getCurrent());
            Event e = eventList.popEvent();
            clock.setCurrent(e.getTimeOfEvent());

            if (e instanceof CloudletArrivalEvent) {                        // Next event is a Cloudlet arrival
                cloudlet.dispatchArrival((CloudletArrivalEvent) e);
                if (!stopArrivals)
                    createNewArrivalEvent();

            }
            else if (e instanceof CloudletCompletionEvent)                   // Next event is a Cloudlet completion
                cloudlet.processCompletion((CloudletCompletionEvent) e);

            else if (e instanceof CloudArrivalEvent)                         // Next event is a Cloud arrival
                cloud.processArrival((CloudArrivalEvent) e);

            else if (e instanceof CloudCompletionEvent)                      // Next event is a Cloud completion
                cloud.processCompletion((CloudCompletionEvent) e);

        }
        BatchMeansStatistics.getMe().printAll();
    }

    /**
     * This method creates a new arrival event
     */

    private void createNewArrivalEvent() {

        Job newJob;

        r.selectStream(1);
        if (rvgs.uniform(0, 1) > LAMBDA1 / (LAMBDA1 + LAMBDA2)) {    // Next event is a class 2 job arrival
            newJob = new Job(clock.getCurrent(), rvgs, (LAMBDA1 + LAMBDA2), 2);
            totalN2++;
        } else {                                                            // Next event is a class 1 job arrival
            newJob = new Job(clock.getCurrent(), rvgs, (LAMBDA1 + LAMBDA2), 1);
            totalN1++;
        }
        Event newEvent = new CloudletArrivalEvent(newJob, newJob.getArrival());
        eventList.pushEvent(newEvent);
    }
}
