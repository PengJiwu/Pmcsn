package progetto;

import rng.Rngs;
import rng.Rvgs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Mmcc_cloud {


    static double START = 0.0;              /* initial time                   */
    static double STOP  = 20000.0;          /* terminal (close the door) time */
    static double INFINITY = 100.0 * STOP;  /* must be much larger than STOP  */

//    static double sarrival = START;              /* Why did I do this?       */

    static double LAMBDA = 6;
    static double MU = 0.45;

    public static void main(String[] args) throws InterruptedException {

        long index  = 0;                  /* used to count departed jobs         */
        long busyServer = 0;                  /* server che so pieni                  */

        Mmcc_cloud s = new Mmcc_cloud();

        Rngs r = new Rngs();
        r.plantSeeds(123456789);
        Rvgs rvgs = new Rvgs(r);
        Timer t = new Timer();

        List jobList = new ArrayList<Job>();

        t.current    = START;           /* set the Clock                         */
//        t.arrival    = s.getArrival(t.arrival, r); /* schedule the first arrival            */
        t.completion = INFINITY;        /* the first event can't be a completion */

        t.arrival = t.current;
//        Job firstJob = new Job(t.current, rvgs , LAMBDA, MU);
//        t.arrival = firstJob.getArrival();
//        jobList.add(firstJob);
//        busyServer++;
        Job nextJob = null;

        MmccArea area = new MmccArea();
        area.initAreaParas();

        while ((t.arrival < STOP) || (busyServer > 0)) {
            t.next          = Math.min(t.arrival, t.completion);  /* next event time   */
            if (busyServer > 0)  {                               /* update integrals  */
                area.node    += (t.next - t.current) * busyServer;
                area.service += (t.next - t.current);

            }
            t.current       = t.next;                    /* advance the Clock */

            if (t.current == t.arrival)  {               /* process an arrival */
                busyServer++;

                Job newJob = new Job(t.current, rvgs, LAMBDA,  MU,1);
                jobList.add(newJob);

                t.arrival     = newJob.getArrival();
                if (t.arrival > STOP)  {
                    t.last      = t.current;
                    t.arrival   = INFINITY;
                }

                nextJob = Mmcc_cloud.getMinCompletion(jobList);
                t.completion = nextJob.getCompletion();
            }
            else {/* process a completion */
                index++;
                busyServer--;
                jobList.remove(nextJob);
                if (busyServer > 0) {
                    nextJob = Mmcc_cloud.getMinCompletion(jobList);
                    t.completion = nextJob.getCompletion();// t.completion = MINIMO DI TUTTI I tfine
                }
                else
                {

                    t.completion = INFINITY;
                    nextJob = null;
                }
            }

//            System.out.println("RESOCONTO CICLO: ");
//            System.out.println("T.CURRENT " + t.current);
//            System.out.println("T.COMPLETION " + t.completion);
//            System.out.println("T.ARRIVAL " +t.arrival);
//            for(Object ob : jobList)
//            {
//                ((Job)ob).printAll();
//            }
//            System.out.println("__________________");
//
//            Thread.sleep(1000);
        }

        DecimalFormat f = new DecimalFormat("###0.00");

        System.out.println("\nfor " + index + " jobs");
        System.out.println("   average interarrival time =   " + f.format(t.last / index));
        System.out.println("   average wait ............ =   " + f.format(area.node / index));
        System.out.println("   average delay ........... =   " + f.format(area.queue / index));
        System.out.println("   average service time .... =   " + f.format(area.service / index));
        System.out.println("   average # in the node ... =   " + f.format(area.node / t.current));
        System.out.println("   average # in the queue .. =   " + f.format(area.queue / t.current));
        System.out.println("   utilization ............. =   " + f.format(area.service / t.current));
    }

    private static Job getMinCompletion(List jobList)
    {
        double minCompletion = 1000*STOP;
        Job nextJob = null;
        for(Object j : jobList)
        {
            Job job = (Job)j;
            if(job.getCompletion() < minCompletion) {
                minCompletion = job.getCompletion();
                nextJob = job;
            }

        }

        return nextJob;

    }

    double exponential(double m, Rngs r) {
/* ---------------------------------------------------
 * generate an Exponential random variate, use m > 0.0
 * ---------------------------------------------------
 */
        return (-m * Math.log(1.0 - r.random()));
    }

    double getArrival(double arrival, Rngs r) {
/* ---------------------------------------------
 * generate the next arrival time, with rate 1/2
 * ---------------------------------------------
 */
        r. selectStream(0);
        arrival += exponential(2.0, r);
        return (arrival);
    }


    double getService(Rngs r) {
/* --------------------------------------------
 * generate the next service time with rate 2/3
 * --------------------------------------------
 */
        r. selectStream(1);
        return 0.0;
        //TODO EXPO;
//        return (uniform(1.0, 2.0, r));
    }





}
