package progetto.Statistics;

/**
 * This class implements Welford Algorithm to calculate mean values iteratively
 */

public class WelfordMean {

    private double current_mean;
    private double current_var;
    private double current_index;

    private double total_v;
    private double total_index;
    private double total_mean;


    public WelfordMean() {

        current_mean = 0.0;
        current_var = 0.0;
        current_index = 0.0;

        total_v = 0.0;
        total_mean = 0.0;
        total_index = 0.0;

    }

    /**
     * This method takes in input a double element and adds it into the Batch
     *
     * @param elem
     */

    public void addBatchElement(double elem) {

        current_index++;

        double old_mean = current_mean;
        double old_var = current_var;

        current_mean = old_mean + (1 / current_index) * (elem - old_mean);
        current_var = old_var + ((current_index - 1.0) / current_index) * ((elem - old_mean) * (elem - old_mean));

        return;
    }

    /**
     * This method takes in input a double element like the previous one but uses indexes that are never reset
     *
     * @param elem
     */

    public void addElement(double elem) {
        total_index++;

        double old_mean = total_mean;
        double old_var = total_v;

        total_mean = old_mean + (1 / total_index) * (elem - old_mean);

        total_v = old_var + ((total_index - 1.0) / total_index) * ((elem - old_mean) * (elem - old_mean));

        return;
    }

    /**
     * This method reset all indexes
     */

    public void resetIndexes() {

        current_mean = 0.0;
        current_var = 0.0;
        current_index = 0.0;

    }

    public double getMean() {
        return current_mean;
    }

    public double getTotal_mean() {
        return total_mean;
    }

    public double getTotalVariance() {
        return (total_v / total_index);
    }

}
