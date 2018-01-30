package progetto;

public class SimulationTimer {
    private double startTime;
    private double duration;
    private double secduration = duration / 1000000000;

    public SimulationTimer(){

        startTime = 0;
        duration = 0;
        secduration = 0;

    }

    public void startTimer(){

        startTime = System.nanoTime();
    }

    public void stopTimer(){

        duration = System.nanoTime() - startTime;
        secduration = duration / 1000000000; //get result in seconds
    }

    public void showTimer(){

        System.out.println("Simulation took " + secduration + " seconds to complete");
    }

}
