package progetto.events;

import progetto.Job;
import rng.Rvgs;

import java.util.*;

public class EventList {

    Rvgs r; // Needed to create an exponential setup time

    private List<Event> cloudletEventList;

    private List<Event> cloudEventList;

    Clock clock;

    private static EventList me = null; //Application of singleton pattern

    public static EventList getEventList() {

        if(me == null) {

            me = new EventList();
            return me;
        }
        else
            return me;
    }

    private EventList () {

        cloudletEventList = new ArrayList<>();
        cloudEventList =  new ArrayList<>();
        clock = Clock.getClock();

    }

    public void pushEvent(Event e) {


        if (e instanceof CloudletArrivalEvent || e instanceof CloudletCompletionEvent) {
            cloudletEventList.add(e);
            Collections.sort(cloudletEventList, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    return (e2.getTimeOfEvent() < e1.getTimeOfEvent()) ? 1 : -1;
                }
            });
        } else if (e instanceof CloudArrivalEvent || e instanceof CloudCompletionEvent) {


            cloudEventList.add(e);
            Collections.sort(cloudEventList, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    return (e2.getTimeOfEvent() < e1.getTimeOfEvent()) ? 1 : -1;
                }
            });
        }

    }

    public Event popEvent() {

        if( cloudletEventList.size() == 0 ){   //No events in cloudlet

            Event e2 = cloudEventList.get(0);
            cloudEventList.remove(0);
            return e2;
        }

       else if( cloudEventList.size() == 0 ) {   //No events in cloud

            Event e1 = cloudletEventList.get(0);
            cloudletEventList.remove(0);
            return e1;
        }

        Event e1 = cloudletEventList.get(0);
        Event e2 = cloudEventList.get(0);

        if (e1.getTimeOfEvent() <= e2.getTimeOfEvent()) {
            cloudletEventList.remove(0);
            return e1;}

        else {
            cloudEventList.remove(0);
            return e2;
        }
    }

    public boolean isEmpty(List<Event> list) {
        return list.isEmpty();
    }

    public Job removeOneC2CompletionEvent() {

        int i = 0;
        ListIterator li = cloudletEventList.listIterator(cloudletEventList.size());
        Event event = null;

        while(li.hasPrevious()) {

            i++;
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

    public void printList(List<Event> list) {

        for(Object o : list) {

            System.out.println("JOB: ");
            ((Event) o).getJob().printAll();
            System.out.println(((Event) o).getTimeOfEvent());

            if(o instanceof CloudletArrivalEvent) {
                System.out.println("Tipo: Arrivo Cloudlet");
            }

            if(o instanceof CloudletCompletionEvent){
                System.out.println("Tipo: Completion Cloudlet");
            }

            if(o instanceof CloudCompletionEvent) {
                System.out.println("Tipo: Completion Cloud");
            }

            if(o instanceof CloudArrivalEvent) {
                System.out.println("Tipo: Arrivo Cloud");
            }
        }
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
