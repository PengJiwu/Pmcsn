package progetto;

import rng.Rvgs;

/**
 * This class models a job
 */

public class Job {

    private double arrival;
    private double firstarrival;

    private double service_time;
    private double completion;
    private double setup_time;
    private int jobClass;
    private boolean prelation = false;

    /**
     * This constructor takes in input some parameters to establish job's class
     *
     * @param current
     * @param r
     * @param lambda
     * @param jobClass
     */

    public Job(double current, Rvgs r, double lambda, int jobClass) {

        arrival = current + r.streamExponential(1 / lambda, 0);
        this.jobClass = jobClass;
    }

    public double getArrival() {
        return arrival;
    }

    public void setArrival(double arrival) {
        this.arrival = arrival;
    }

    public double getService_time() {
        return service_time;
    }

    public void setService_time(double service_time) {
        this.service_time = service_time;
    }

    public double getCompletion() {
        return completion;
    }

    public void setCompletion(double completion) {
        this.completion = completion;
    }

    public int getJobClass() {
        return jobClass;
    }

    public boolean isPrelated() {
        return prelation;
    }

    public void setPrelation(boolean prelation) {
        this.prelation = prelation;
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

}
