package progetto;

import rng.Rngs;
import ssq.Ssq3;
import ssq.Ssq3Area;
import ssq.Ssq3T;

import java.text.DecimalFormat;


public class Mmcc {


    static double START = 0.0;              /* initial time                   */
    static double STOP  = 20000.0;          /* terminal (close the door) time */
    static double INFINITY = 100.0 * STOP;  /* must be much larger than STOP  */

//    static double sarrival = START;              /* Why did I do this?       */

    static double LAMBDA = 6;
    static double MU = 0.45;

    public static void main(String[] args) {

        long index  = 0;                  /* used to count departed jobs         */
        long busyServer = 0;                  /* server che so pieni                  */

        Mmcc s = new Mmcc();

        Rngs r = new Rngs();
        r.plantSeeds(123456789);

        Timer t = new Timer();
        t.current    = START;           /* set the clock                         */
        t.arrival    = s.getArrival(t.arrival, r); /* schedule the first arrival            */
        t.completion = INFINITY;        /* the first event can't be a completion */

        MmccArea area = new MmccArea();
        area.initAreaParas();

        while ((t.arrival < STOP) || (busyServer > 0)) {
            t.next          = Math.min(t.arrival, t.completion);  /* next event time   */
            if (busyServer > 0)  {                               /* update integrals  */
                area.node    += (t.next - t.current) * busyServer;
                area.service += (t.next - t.current);

            }
            t.current       = t.next;                    /* advance the clock */

            if (t.current == t.arrival)  {               /* process an arrival */
                busyServer++;
                t.arrival     = s.getArrival(t.arrival,r);
                if (t.arrival > STOP)  {
                    t.last      = t.current;
                    t.arrival   = INFINITY;
                }
                //GENERA STRUTTURA NUOVO JOB (t arrivo , t compl, t fine = arrivo + compl)

                if (busyServer >= 0)
                    t.completion = t.current + s.getService(r); // t.completion = MINIMO DI TUTTI I tfine
            }
            else {/* process a completion */
                index++;
                busyServer--;
                if (busyServer > 0)
                    //ESCLUDI QUEL JOB
                    t.completion = t.current + s.getService(r); // t.completion = MINIMO DI TUTTI I tfine
                else
                    t.completion = INFINITY;
            }
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
