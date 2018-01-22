package progetto.events;

import java.util.ArrayList;

public class Clock {

    private double current;                 /* current time                        */
    private double next;                    /* next (most imminent) event time     */
    private double last;                    /* last arrival time                   */

    static double START = 0.0;              /* initial time                   */
    static double STOP = 20000.0;          /* terminal (close the door) time */
    static double INFINITY = 100.0 * STOP;

    static Clock me = null;

    public static Clock getClock(){
        if(me == null)
        {
            me = new Clock();
            return me;
        }
        else
            return me;
    }


    private Clock() {

        current = START;
        next = INFINITY;
        last = INFINITY;

    }


    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getNext() {
        return next;
    }

    public void setNext(double next) {
        this.next = next;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

}
