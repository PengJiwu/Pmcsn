package progetto.events;

import progetto.Job;

public class CloudCompletionEvent extends Event{

    public CloudCompletionEvent(Job job, double timeOfEvent) {
        super(job, timeOfEvent);
    }
}
