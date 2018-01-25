package progetto.Statistics;

public class WelfordMean {

    //Welford Algorithm to calculate mean values iteratively.


    private double current_mean;
    private double current_var;
    private double current_index;

    public WelfordMean() {

        current_mean = 0.0;
        current_var = 0.0;
        current_index = 0.0;
    }

    /**
     *
     * @return the mean at current state
     */
    public double getMean() {
        return current_mean;
    }

    /**
     *
     * @return the variance at current state
     */
    public double getVariance() {return current_var;}
    /**
     *
     * @param elem
     * @return
     */
    public void addElement(double elem) {
        current_index++;

        double old_mean = current_mean;
        double old_var = current_var;


        current_mean = old_mean + (1/current_index)*(elem- old_mean);

        current_var = old_var +  ((current_index-1.0)/current_index)*((elem-old_mean)*(elem-old_mean));
        if (current_index >1)
            current_var = current_var / (current_index-1.0);


        System.out.println("mean is " + current_mean+"\n");
        System.out.println("var is " + current_var+"\n");

       // System.out.println("Current_Var is " + current_var);

        return;
    }


    public void resetIndexes(){

        current_mean = 0.0;
        current_var = 0.0;
        current_index = 0.0;

    }
}
