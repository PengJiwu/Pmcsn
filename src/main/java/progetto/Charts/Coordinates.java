package progetto.Charts;

public class Coordinates {


    private double time;
    private double cloudletPopulation_n1;
    private double cloudletPopulation_n2;
    private double cloudPopulation_n1;
    private double cloudPopulation_n2;

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getCloudletPopulation_n1() {
        return cloudletPopulation_n1;
    }

    public void setCloudletPopulation_n1(double cloudletPopulation_n1) {
        this.cloudletPopulation_n1 = cloudletPopulation_n1;
    }

    public double getCloudletPopulation_n2() {
        return cloudletPopulation_n2;
    }

    public void setCloudletPopulation_n2(double cloudletPopulation_n2) {
        this.cloudletPopulation_n2 = cloudletPopulation_n2;
    }

    public double getCloudPopulation_n1() {
        return cloudPopulation_n1;
    }

    public void setCloudPopulation_n1(double cloudPopulation_n1) {
        this.cloudPopulation_n1 = cloudPopulation_n1;
    }

    public double getCloudPopulation_n2() {
        return cloudPopulation_n2;
    }

    public void setCloudPopulation_n2(double cloudPopulation_n2) {
        this.cloudPopulation_n2 = cloudPopulation_n2;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "time=" + time +
                ", cloudletPopulation_n1=" + cloudletPopulation_n1 +
                ", cloudletPopulation_n2=" + cloudletPopulation_n2 +
                ", cloudPopulation_n1=" + cloudPopulation_n1 +
                ", cloudPopulation_n2=" + cloudPopulation_n2 +
                '}';
    }
}
