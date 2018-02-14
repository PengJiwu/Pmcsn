package progetto;

/**
 * This class has the only purpose to measure simulation length
 */

public class SimulationTimer {

    private double startTime;
    private double duration;

    public SimulationTimer() {

        startTime = 0;
        duration = 0;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        duration = System.nanoTime() - startTime;
    }

    public void showTimer() {
        System.out.println("Simulation took " + duration + " seconds to complete");
    }

}
