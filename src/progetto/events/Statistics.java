package progetto.events;

import progetto.MmccArea;

import java.text.DecimalFormat;

public class Statistics {

    private MmccArea areaN1Cloudlet;
    private MmccArea areaN2Cloudlet;
    private MmccArea areatotCloudlet;

    private MmccArea areaN1Cloud;
    private MmccArea areaN2Cloud;
    private MmccArea areatotCloud;

    private int cloudletN1 = 0, cloudletN2 = 0;
    private int cloudN1 = 0, cloudN2 = 0;

    private int completedN1cloudlet = 0;
    private int completedN2cloudlet = 0;

    private int completedN1cloud = 0;
    private int completedN2cloud = 0;

    private int totalN1 = 0;
    private int totalN2 = 0;

    private static Statistics me = null;




    private Clock clock = Clock.getClock();

    private Statistics(){

        areaN1Cloudlet = new MmccArea();
        areaN2Cloudlet = new MmccArea();
        areatotCloudlet = new MmccArea();
        areaN1Cloud = new MmccArea();
        areaN2Cloud = new MmccArea();
        areatotCloud = new MmccArea();

    }

    public static Statistics getMe() {
        if (me == null){

            me = new Statistics();
            return  me;

        }

        return me;
    }

    public void updateCloudletStatistics(int a, int b, int c, int d, int e, int f)
    {

        cloudletN1 = a;
        cloudletN2 = b;

        totalN1 = c;
        totalN2 = d;

        completedN1cloudlet = e;
        completedN2cloudlet = f;


      //  if (cloudletN1 > 0) {                               /* update integrals  */
            areaN1Cloudlet.node += (clock.getCurrent() - clock.getPrevious()) * cloudletN1;
            areaN1Cloudlet.service += (clock.getCurrent() - clock.getPrevious());
       // }

        //if (cloudletN2 > 0) {                               /* update integrals  */
            areaN2Cloudlet.node += (clock.getCurrent() - clock.getPrevious()) * cloudletN2;
            areaN2Cloudlet.service += clock.getCurrent() - clock.getPrevious();
     //   }

        //if (cloudletN1 + cloudletN2 > 0) {                               /* update integrals  */
            areatotCloudlet.node += (clock.getCurrent() - clock.getPrevious()) * (cloudletN1 + cloudletN2);
            areatotCloudlet.service += clock.getCurrent() - clock.getPrevious();
     //   }


    }


    public void updateCloudStatistics(int n1, int n2, int completedN1, int completedN2) {

        cloudN1 = n1;
        cloudN2 = n2;

        completedN1cloud = completedN1;
        completedN2cloud = completedN2;

        if (cloudN1 > 0) {                               /* update integrals  */
            areaN1Cloud.node += (clock.getCurrent() - clock.getPrevious()) * cloudN1;
            areaN1Cloud.service += (clock.getCurrent() - clock.getPrevious());
        }

        if (cloudN2 > 0) {                               /* update integrals  */
            areaN2Cloud.node += (clock.getCurrent() - clock.getPrevious()) * cloudN2;
            areaN2Cloud.service += clock.getCurrent() - clock.getPrevious();
        }

        if (cloudN1 + cloudN2 > 0) {                               /* update integrals  */
            areatotCloud.node += (clock.getCurrent() - clock.getPrevious()) * (cloudN1 + cloudN1);
            areatotCloud.service += clock.getCurrent() - clock.getPrevious();
        }


    }





    public void printStatistics() {

        DecimalFormat f = new DecimalFormat("###0.00");

        int total = totalN1 + totalN2;

        System.out.println("\n job totali:  " + totalN1 + " & " + totalN2 );
        System.out.println("\n job totali:  " + total );

        System.out.println("job class 1 completati dal cloudlet " + completedN1cloudlet);
        System.out.println("job class 2 completati dal cloudlet " + completedN2cloudlet);

        System.out.println("job class 1 completati dal cloud " + completedN1cloud);
        System.out.println("job class 2 completati dal cloud " + completedN2cloud);

        int cloudletTotCompl = completedN1cloudlet + completedN2cloudlet;
        double thr = cloudletTotCompl / areatotCloudlet.service;
        System.out.println("Throughput del cloudlet: " + thr);


        System.out.println("   average interarrival time  class 1 =   " + f.format(clock.getLast() / totalN1));
        System.out.println("   average interarrival time  class 2 =   " + f.format(clock.getLast() / totalN2));
        System.out.println("   average interarrival time  tot     =   " + f.format(clock.getLast() / total));

        //TODO CHECK IF CORRECT!!!
//        System.out.println("   average wait class1............ =   " + f.format(areaN1.node / completedN1));
//        System.out.println("   average wait class2............ =   " + f.format(areaN2.node / completedN2));
//        System.out.println("   average wait tot............ =      " + f.format(areaTot.node / (completedN1 + completedN2)));

        System.out.println("   average service time class 1 cloudlet .... =   " + f.format(areaN1Cloudlet.service / completedN1cloudlet));
        System.out.println("   average service time class 2 cloudlet.... =    " + f.format(areaN2Cloudlet.service / completedN2cloudlet));
        System.out.println("   average service time tot.... =        " + f.format(areatotCloudlet.service / (completedN1cloudlet + completedN2cloudlet)));

        System.out.println("   average service time class 1 cloud   .... =   " + f.format(areaN1Cloud.service / completedN1cloud));
        System.out.println("   average service time class 2 cloud   .... =    " + f.format(areaN2Cloud.service / completedN2cloud));
        System.out.println("   average service time tot.... =        " + f.format(areatotCloud.service / (completedN1cloud + completedN2cloud)));

        System.out.println("   average class 1 # in the cloudlet ... =   " + f.format(areaN1Cloudlet.node / clock.getCurrent()));
        System.out.println("   average class 2 # in the cloudlet ... =   " + f.format(areaN2Cloudlet.node / clock.getCurrent()));
        System.out.println("   average total # in the cloudlet ... =   " + f.format(areatotCloudlet.node / clock.getCurrent()));


        System.out.println("   average class 1 # in the cloud ... =   " + f.format(areaN1Cloud.node / clock.getCurrent()));
        System.out.println("   average class 2 # in the cloud... =   " + f.format(areaN2Cloud.node / clock.getCurrent()));
        System.out.println("   average total # in the cloud ... =   " + f.format(areatotCloud.node / clock.getCurrent()));

        System.out.println("   utilization classe 1  ............. =   " + f.format(areaN1Cloudlet.service / clock.getCurrent()));
        System.out.println("   utilization classe 2............. =   " + f.format(areaN2Cloudlet.service / clock.getCurrent()));
        System.out.println("   utilization ............. =   " + f.format(areatotCloudlet.service / clock.getCurrent()));

        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }




}
