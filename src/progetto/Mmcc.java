package progetto;

import rng.Rngs;
import rng.Rvgs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Mmcc {


    static double START = 0.0;              /* initial time                   */
    static double STOP = 20000.0;          /* terminal (close the door) time */
    static double INFINITY = 100.0 * STOP;  /* must be much larger than STOP  */

//    static double sarrival = START;              /* Why did I do this?       */

    static double LAMBDA1 = 6;
    static double MU1clet = 0.45;
    static double LAMBDA2 = 6.25;
    static double MU2clet = 0.27;
    static int N = 20;
    static int S = 20;

    public static void main(String[] args) throws InterruptedException {


        long totalN1 = 0;
        long totalN2 = 0;

        long indexN1 = 0;/* used to count departed jobs         */
        long indexN2 = 0;

        Mmcc s = new Mmcc();

        Rngs r = new Rngs();
        r.plantSeeds(123456789);
        Rvgs rvgs = new Rvgs(r);
        Timer t = new Timer();

        List jobList = new ArrayList<Job>();

        int n1 = 0, n2 = 0;

        t.current = START;           /* set the clock                         */
        t.completion = INFINITY;        /* the first event can't be a completion */
        t.arrival = t.current;
        Job nextCompletionJob = null;
        Job nextArrivalJob = null;
        MmccArea area1 = new MmccArea();
        MmccArea area2 = new MmccArea();
        MmccArea areaTot = new MmccArea();
        area1.initAreaParas();
        area2.initAreaParas();
        areaTot.initAreaParas();

        while ((t.arrival < STOP) || (n1 + n2 > 0)) {
            t.next = Math.min(t.arrival, t.completion);  /* next event time   */

            if (n1 > 0) {                               /* update integrals  */
                area1.node += (t.next - t.current) * n1;
                area1.service += (t.next - t.current);
            }

            if (n2 > 0) {                               /* update integrals  */
                area2.node += (t.next - t.current) * n2;
                area2.service += (t.next - t.current);
            }

            if (n1 + n2 > 0) {                               /* update integrals  */
                areaTot.node += (t.next - t.current) * (n1 + n2);
                areaTot.service += (t.next - t.current);
            }


            t.current = t.next;                    /* advance the clock */

            if (t.current == t.arrival) {               /* process an arrival */
                if (nextArrivalJob != null)
                    if (nextArrivalJob.getClasse() == 1) {
                        //Arriva job di classe 1

                        if (n1 == N) {

                            Mmcc.sendToTheCloud(nextArrivalJob);  //TODO
                            jobList.remove(nextArrivalJob);
                        } else if (n1 + n2 < S) {
                            n1++;
                        } else if (n2 > 0) {
                            Job jobToSend = Mmcc.selectOneC2Job(jobList);
                            jobList.remove(jobToSend);
                            Mmcc.sendToTheCloud(jobToSend);
                            n1++;
                            n2--;
                                        /*Caso limite in cui ad essere eliminato è il job successivo ad essere completato*/
                            if (jobToSend.getCompletion() == t.completion)
                            {
                                nextCompletionJob = Mmcc.getMinCompletion(jobList);
                                t.completion = nextCompletionJob.getCompletion();// t.completion = MINIMO DI TUTTI I tfine
                            }

                        } else
                            n1++;
                    } else {
                        //Arriva job di classe 2
                        if (n1 + n2 >= S) {
                            Mmcc.sendToTheCloud(nextArrivalJob);
                            jobList.remove(nextArrivalJob);
                        } else
                            n2++;
                    }



                /*
                 fare controlli su nextArrivalJob

                 */

                Job newJob;
                if (rvgs.uniform(0, 1) > LAMBDA1 / (LAMBDA1 + LAMBDA2)) {
                    /*Il prossimo arrivo sarà job di classe 2 */
                    newJob = new Job(t.current, rvgs, (LAMBDA1 + LAMBDA2), MU2clet, 2);
                    totalN2++;
                } else {
                    /*Il prossimo arrivo sarà job di classe 1*/
                    newJob = new Job(t.current, rvgs, (LAMBDA1 + LAMBDA2), MU1clet, 1);
                    totalN1++;
                }
                jobList.add(newJob);

                t.arrival = newJob.getArrival();
                nextArrivalJob = newJob;
                if (t.arrival > STOP) {
                    t.last = t.current;
                    t.arrival = INFINITY;
                }

                nextCompletionJob = Mmcc.getMinCompletion(jobList);
                t.completion = nextCompletionJob.getCompletion();
            } else {/* process a completion */
                if (nextCompletionJob.getClasse() == 1) {
                    n1--;
                    indexN1++;
                } else {
                    n2--;
                    indexN2++;
                }
                jobList.remove(nextCompletionJob);
                if (n1 + n2 > 0) {
                    nextCompletionJob = Mmcc.getMinCompletion(jobList);
                    t.completion = nextCompletionJob.getCompletion();// t.completion = MINIMO DI TUTTI I tfine
                } else {
                    t.completion = INFINITY;
                    nextCompletionJob = null;
                }
            }
//
//            System.out.println("RESOCONTO CICLO: ");
//            System.out.println("n1: " + n1 + " n2: " + n2);
//            System.out.println("T.CURRENT          " + t.current);
//            System.out.println("T.COMPLETION       " + t.completion);
//            System.out.println("T.ARRIVAL          " +t.arrival);
//            for(Object ob : jobList)
//            {
//                ((Job)ob).printAll();
//            }
//            System.out.println("__________________");
//
//            Thread.sleep(1000);
        }

        DecimalFormat f = new DecimalFormat("###0.00");

        System.out.println("\n job totali:  " + totalN1 + " & " + totalN2);
        System.out.println("job class 1 completati " + indexN1);
        System.out.println("job class 2 completati " + indexN2);

        System.out.println("   average interarrival time  class 1=   " + f.format(t.last / totalN1));
        System.out.println("   average interarrival time  class 2=   " + f.format(t.last / totalN2));
        System.out.println("   average interarrival time  tot=       " + f.format(t.last / (totalN1 + totalN2)));

        System.out.println("   average wait class1............ =   " + f.format(area1.node / indexN1));
        System.out.println("   average wait class2............ =   " + f.format(area2.node / indexN2));
        System.out.println("   average wait tot............ =      " + f.format(areaTot.node / (indexN1 + indexN2)));

        System.out.println("   average service time class 1 .... =   " + f.format(area1.service / indexN1));
        System.out.println("   average service time class 2.... =    " + f.format(area2.service / indexN2));
        System.out.println("   average service time tot.... =        " + f.format(areaTot.service / (indexN1 + indexN2)));

        System.out.println("   average # in the node ... =   " + f.format(area1.node / t.current));
        System.out.println("   average # in the node ... =   " + f.format(area2.node / t.current));
        System.out.println("   average # in the node ... =   " + f.format(areaTot.node / t.current));

        System.out.println("   utilization ............. =   " + f.format(area1.service / t.current));
        System.out.println("   utilization ............. =   " + f.format(area2.service / t.current));
        System.out.println("   utilization ............. =   " + f.format(areaTot.service / t.current));
    }

    private static Job getMinCompletion(List jobList) {
        double minCompletion = 1000 * STOP;
        Job nextJob = null;
        for (Object j : jobList) {
            Job job = (Job) j;
            if (job.getCompletion() < minCompletion) {
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
        r.selectStream(0);
        arrival += exponential(2.0, r);
        return (arrival);
    }


    double getService(Rngs r) {
/* --------------------------------------------
 * generate the next service time with rate 2/3
 * --------------------------------------------
 */
        r.selectStream(1);
        return 0.0;
        //TODO EXPO;
//        return (uniform(1.0, 2.0, r));
    }

    private static void sendToTheCloud(Job job) {
//        System.out.println("Job al cloud: ");
//        job.printAll();


    }

    /**
     * Seleziona un C2 Job secondo una metrica <- prende quello con completion più grande
     *
     * @param jobList
     * @return
     */
    private static Job selectOneC2Job(List jobList) {

        double maxCompletion = 0;
        Job jobToSend = null;
        for (Object j : jobList) {
            Job job = (Job) j;
            if (job.getClasse() == 2)
                if (job.getCompletion() > maxCompletion) {
                    maxCompletion = job.getCompletion();
                    jobToSend = job;
                }

        }
        return jobToSend;

    }


}
