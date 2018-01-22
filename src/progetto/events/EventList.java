package progetto.events;

import progetto.Job;

import java.util.*;

public class EventList {

    List<Event> eventList;
    Clock clock;
    static double STOP = 20000.0;          /* terminal (close the door) time */
    static double INFINITY = 100.0 * STOP;

    private static EventList me = null;

    public static EventList getEventList(){
        if(me == null)
        {
            me = new EventList();
            return me;
        }
        else
            return me;
    }

    private EventList ()
    {
        eventList = new ArrayList<>();
        clock = Clock.getClock();
    }

    public void pushEvent(Event e)
    {

        eventList.add(e);


        Collections.sort(eventList, new Comparator<Event>() {
            @Override public int compare(Event e1, Event e2) {
                return(e2.getTimeOfEvent() < e1.getTimeOfEvent())?1:-1;


        }});
    }

    public Event popEvent()
    {

        Event e = eventList.get(0);
        eventList.remove(0);
        return e;
    }

    public boolean isEmpty()
    {
        return eventList.isEmpty();

    }

    public void removeOneC2CompletionEvent()
    {


        System.out.println("STO QUA");
        ListIterator li = eventList.listIterator(eventList.size());

        while(li.hasPrevious())
        {
            Event e = (Event) li.previous();

            if(e instanceof CloudletCompletionEvent)

                if(e.getJob().getClasse() == 2)
                {

                    eventList.remove(e);
                    Job job = e.getJob();
                    job.setArrival(clock.getCurrent());
                    job.setCompletion(INFINITY);
                    job.setService_time(INFINITY);
                    Event newEvent = new CloudArrivalEvent(job,clock.getCurrent());
                    this.pushEvent(newEvent);


                }




        }


            this.printList();
    }

    public void printList()
    {
        for(Object o : eventList) {
            System.out.println("JOB: ");
            ((Event) o).getJob().printAll();
            System.out.println(((Event) o).getTimeOfEvent());

            if(o instanceof CloudletArrivalEvent)
            {
                System.out.println("Tipo: Arrivo Cloudlet");
            }

            if(o instanceof CloudletCompletionEvent){
                System.out.println("Tipo: Completion Cloudlet");


            }

            if(o instanceof CloudCompletionEvent)
            {
                System.out.println("Tipo: Completion Cloud");

            }

            if(o instanceof CloudArrivalEvent)
            {
                System.out.println("Tipo: Arrivo Cloud");

            }


        }



    }
}
