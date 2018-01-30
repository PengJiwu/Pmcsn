package progetto;

import rng.Rngs;
import rng.Rvgs;

public class Job {

    private double arrival;
    private double firstarrival;

    private  double service_time;
    private double completion;
    private double setup_time;
    private int index;
    private int classe;
    private boolean prelation = false;

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
        if (classe ==1)
         service_time = r.streamExponential(1/mu, 7);
        else
            service_time = r.streamExponential(1/mu, 8);
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


    public double getFirstarrival() {
        return firstarrival;
    }

    public void setFirstarrival(double firstarrival) {
        this.firstarrival = firstarrival;
    }

    public double getSetup_time() {
        return setup_time;
    }

    public void setSetup_time(double setup_time) {
        this.setup_time = setup_time;
    }

    public boolean getPrelation() {
        return prelation;
    }
}
