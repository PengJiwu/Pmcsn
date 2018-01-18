package progetto;

public class Job {

    double arrival;
    double service_time;
    double completion;
    int index;
    int classe;
    boolean prelation;

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
}
