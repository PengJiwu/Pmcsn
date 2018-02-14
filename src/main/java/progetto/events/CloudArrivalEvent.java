package progetto.events;

import progetto.Job;

public class CloudArrivalEvent extends Event {

    public CloudArrivalEvent(Job job, double timeOfEvent) {
        super(job, timeOfEvent);
    }
}
