package progetto.events;

import progetto.Job;

public class CloudletCompletionEvent extends Event {
    public CloudletCompletionEvent(Job job, double timeOfEvent) {
        super(job, timeOfEvent);
    }
}
