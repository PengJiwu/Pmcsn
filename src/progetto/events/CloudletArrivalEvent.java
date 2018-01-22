package progetto.events;

import progetto.Job;

public class CloudletArrivalEvent extends Event{

    public CloudletArrivalEvent(Job job, double timeOfEvent) {
        super(job, timeOfEvent);
    }
}
