package progetto;



public class MmccArea {
    public double node;                    /* time integrated number in the node  */
    public double queue;                   /* time integrated number in the queue */
    public double service;                 /* time integrated number in service   */

    public void initAreaParas() {
        node = 0.0;
        queue = 0.0;
        service = 0.0;
    }
}