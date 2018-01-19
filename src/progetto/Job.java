package progetto;

import rng.Rngs;
import rng.Rvgs;

public class Job {

    private double arrival;
    private  double service_time;
    private double completion;
    private int index;
    private int classe;
    private boolean prelation;

    public double getArrival() {
        return arrival;
    }

    public double getService_time() {
        return service_time;
    }

    public double getCompletion() {
        return completion;
    }

    public int getIndex() {
        return index;
    }

    public int getClasse() {
        return classe;
    }

    public boolean isPrelation() {
        return prelation;
    }

    public void setArrival(double arrival) {
        this.arrival = arrival;
    }

    public void setService_time(double service_time) {
        this.service_time = service_time;
    }

    public void setCompletion(double completion) {
        this.completion = completion;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public void setPrelation(boolean prelation) {
        this.prelation = prelation;
    }

    public Job(double current, Rvgs r, double lambda, double mu,int classe)
    {


        arrival = current + r.streamExponential(1/lambda, 0);

        service_time = r.streamExponential(1/mu, 1);

        completion = arrival + service_time;

        this.classe = classe;
    }

    public void printAll()
    {

        System.out.println("JOB di tipo " + classe);
        System.out.println("ARRIVO:     " + arrival);
        System.out.println("TSERV       " + service_time);
        System.out.println("TCOMPL      " + completion + "\n");

    }



}
