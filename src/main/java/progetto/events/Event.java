package progetto.events;

import progetto.Job;

/**
 * This class implements a generic event
 */

public abstract class Event {

    private Job job;
    private double timeOfEvent;

    public Event(Job job, double timeOfEvent) {

        this.job = job;
        this.timeOfEvent = timeOfEvent;

    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public double getTimeOfEvent() {
        return timeOfEvent;
    }

    public void setTimeOfEvent(double timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }
}
