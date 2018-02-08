package progetto.events;

import progetto.Job;
import rng.Rvgs;

import java.util.*;

/**
 * This class implements the singleton pattern for a general shared list of events
 */

public class EventList {

    private List<Event> cloudletEventList;

    private List<Event> cloudEventList;

    Clock clock;

    private static EventList me = null;             //Application of singleton pattern

    public static EventList getEventList() {

        if (me == null) {

            me = new EventList();
            return me;
        }
        else
            return me;
    }

    /**
     * This constructor creates list of Cloud and Cloudlet events
     */

    private EventList () {

        cloudletEventList = new ArrayList<>();
        cloudEventList =  new ArrayList<>();
        clock = Clock.getClock();

    }

    /**
     * This method takes in input an event and pushes it into the list
     * @param e
     */

    public void pushEvent(Event e) {

        if (e instanceof CloudletArrivalEvent || e instanceof CloudletCompletionEvent) {    // Cloudlet event
            cloudletEventList.add(e);
            Collections.sort(cloudletEventList, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    return (e2.getTimeOfEvent() < e1.getTimeOfEvent()) ? 1 : -1;
                }
            });
        }
        else if (e instanceof CloudArrivalEvent || e instanceof CloudCompletionEvent) {     // Cloud event

            cloudEventList.add(e);
            Collections.sort(cloudEventList, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    return (e2.getTimeOfEvent() < e1.getTimeOfEvent()) ? 1 : -1;
                }
            });

        }
    }

    /**
     * This method removes first event of the list
     * @return
     */

    public Event popEvent() {

        if (cloudletEventList.size() == 0 ){                 //No events in Cloudlet

            Event e2 = cloudEventList.get(0);
            cloudEventList.remove(0);
            return e2;
        }
        else if (cloudEventList.size() == 0 ) {             //No events in Cloud

            Event e1 = cloudletEventList.get(0);
            cloudletEventList.remove(0);
            return e1;
        }

        Event e1 = cloudletEventList.get(0);
        Event e2 = cloudEventList.get(0);

        if (e1.getTimeOfEvent() <= e2.getTimeOfEvent()) {   // Gets minimum
            cloudletEventList.remove(0);
            return e1;
        }

        else {
            cloudEventList.remove(0);
            return e2;
        }
    }

    /**
     * This method removes a completion event of class 2 job from the Cloudlet list
     * Called whenever a prelation happens
     * @return
     */

    public Job removeOneC2CompletionEvent() {

        ListIterator li = cloudletEventList.listIterator(cloudletEventList.size());
        Event event = null;

        while (li.hasPrevious()) {

            Event e = (Event) li.previous();

            if (e instanceof CloudletCompletionEvent)
                if(e.getJob().getJobClass() == 2) {

                    event = e;
                    break;
                }
        }

        cloudletEventList.remove(event);
        Job job = event.getJob();

        return job;
    }

    public boolean isEmpty(List<Event> list) {
        return list.isEmpty();
    }

    public List<Event> getCloudletEventList() {
        return cloudletEventList;
    }

    public void setCloudletEventList(List<Event> cloudletEventList) {
        this.cloudletEventList = cloudletEventList;
    }

    public List<Event> getCloudEventList() {
        return cloudEventList;
    }

    public void setCloudEventList(List<Event> cloudEventList) {
        this.cloudEventList = cloudEventList;
    }

}
