package progetto.events;


/**
 * This class implements the clock of the simulation
 */

public class Clock {

    static double START = 0.0;              // initial time
    static double STOP = 100.0;             // terminal (close the door) time
    static double INFINITY = 100.0 * STOP;
    static Clock me = null;
    private double current;                 // current time
    private double next;                    // next (most imminent) event time
    private double last;
    private double previous;

    private Clock() {

        current = START;
        next = INFINITY;
        last = INFINITY;
        previous = INFINITY;

    }

    /**
     * This method implements singleton pattern, allowing a global shared clock
     *
     * @return
     */

    public static Clock getClock() {

        if (me == null) {

            me = new Clock();
            return me;
        } else
            return me;

    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public void setPrevious(double previous) {
        this.previous = previous;
    }

}
