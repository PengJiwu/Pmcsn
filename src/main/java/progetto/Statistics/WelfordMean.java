package progetto.Statistics;

public class WelfordMean {

    //Welford Algorithm to calculate mean values iteratively.


    private double current_mean;
    private double current_var;
    private double current_index;


    private double total_var;
    private double total_index;
    private double total_mean;


    public WelfordMean() {

        current_mean = 0.0;
        current_var = 0.0;
        current_index = 0.0;

        total_var = 0.0;
        total_mean = 0.0;

    }

    /**
     *
     * @return the mean at current state
     */
    public double getMean() {

        return current_mean;
    }


    public double getVariance() {
        return current_var;
    }

    public void addBatchElement(double elem) {
        current_index++;

        double old_mean = current_mean;
        double old_var = current_var;


        current_mean = old_mean + (1/current_index)*(elem- old_mean);

        current_var = old_var +  ((current_index-1.0)/current_index)*((elem-old_mean)*(elem-old_mean));
        if (current_index >1)
            current_var = current_var / (current_index-1.0);

        return;
    }

    public double getTotal_var() {
        return total_var;
    }

    public void addElement(double elem) {
        total_index++;




        total_mean = total_mean + (1/total_index)*(elem- total_mean);

        total_var = total_var +  ((total_index-1.0)/total_index)*((elem-total_mean)*(elem-total_mean));
        if (total_index >1)
            total_var= total_var/ (total_index-1.0);

        return;
    }





    public void resetIndexes(){

        current_mean = 0.0;
        current_var = 0.0;
        current_index = 0.0;

    }
}
