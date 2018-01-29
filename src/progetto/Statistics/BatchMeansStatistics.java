package progetto.Statistics;

public class BatchMeansStatistics {

    private static BatchMeans systemUtilization;                   //necessary for computation

    private static BatchMeans systemRTime;                         //requested ok
    private static BatchMeans systemClassI_RTime;                  //requested ok
    private static BatchMeans systemClassII_RTime;                 //requested ok


    private static BatchMeans systemThroughput;                    //requested ok
    private static BatchMeans systemThroughput_ClassI;             //requested ok
    private static BatchMeans systemThroughput_ClassII;            //requested ok


    private static BatchMeans cloudletThroughput;                  //requested ok
    private static BatchMeans cloudletThroughput_ClassI;           //requested ok
    private static BatchMeans cloudletThroughput_ClassII;          //requested ok


    private static BatchMeans interruptedTasks_classIIpercentage;  //requested ok
    private static BatchMeans interruptedTasks_classII_RTime;      //requested ok


    private static BatchMeans cloudletPopulation;                  //requested ok
    private static BatchMeans cloudletClassI_Population;           //requested ok
    private static BatchMeans cloudletClassII_Population;          //requested ok


    private static BatchMeans cloudPopulation;                     //requested ok
    private static BatchMeans cloudClassI_Population;              //requested ok
    private static BatchMeans cloudClassII_Population;             //requested ok


    private static BatchMeans cloudletRTime;             //necessary for computation
    private static BatchMeans cloudletClassI_RTime;             //necessary for computation ok
    private static BatchMeans cloudletClassII_RTime;             //necessary for computation ok

    private static BatchMeans cloudRTime;             //necessary for computation
    private static BatchMeans cloudClassI_RTime;             //necessary for computation ok
    private static BatchMeans cloudClassII_RTime;             //necessary for computation ok

    private static BatchMeans cloudThroughput;                 //necessary for computation
    private static BatchMeans cloudThroughput_ClassI;             //necessary for computation
    private static BatchMeans cloudThroughput_ClassII;             //necessary for computation


    public static BatchMeansStatistics getMe() {

        if (me == null) {

            me = new BatchMeansStatistics();
            return me;

        }

        return me;
    }

    static BatchMeansStatistics me = null;


    private BatchMeansStatistics() {

        // systemUtilization = new BatchMeans();

        systemRTime = new BatchMeans();
        systemClassI_RTime = new BatchMeans();
        systemClassII_RTime = new BatchMeans();

        systemThroughput = new BatchMeans();
        systemThroughput_ClassI = new BatchMeans();
        systemThroughput_ClassII = new BatchMeans();

        cloudletThroughput = new BatchMeans();
        cloudletThroughput_ClassI = new BatchMeans();
        cloudletThroughput_ClassII = new BatchMeans();

        cloudletThroughput_ClassI = new BatchMeans();
        cloudletThroughput_ClassII = new BatchMeans();

        cloudThroughput_ClassI = new BatchMeans();
        cloudThroughput_ClassII = new BatchMeans();

        interruptedTasks_classIIpercentage = new BatchMeans();
        interruptedTasks_classII_RTime = new BatchMeans();

        cloudletPopulation = new BatchMeans();
        cloudletClassI_Population = new BatchMeans();
        cloudletClassII_Population = new BatchMeans();

        cloudPopulation = new BatchMeans();
        cloudClassI_Population = new BatchMeans();
        cloudClassII_Population = new BatchMeans();

        cloudletClassI_RTime = new BatchMeans();
        cloudletClassII_RTime = new BatchMeans();

        cloudClassI_RTime = new BatchMeans();
        cloudClassII_RTime = new BatchMeans();



    }


    public static BatchMeans getSystemUtilization() {
        return systemUtilization;
    }

    public static void setSystemUtilization(BatchMeans systemUtilization) {
        BatchMeansStatistics.systemUtilization = systemUtilization;
    }

    public static BatchMeans getSystemRTime() {
        return systemRTime;
    }

    public static void setSystemRTime(BatchMeans systemRTime) {
        BatchMeansStatistics.systemRTime = systemRTime;
    }

    public static BatchMeans getSystemClassI_RTime() {
        return systemClassI_RTime;
    }

    public static void setSystemClassI_RTime(BatchMeans systemClassI_RTime) {
        BatchMeansStatistics.systemClassI_RTime = systemClassI_RTime;
    }

    public static BatchMeans getSystemClassII_RTime() {
        return systemClassII_RTime;
    }

    public static void setSystemClassII_RTime(BatchMeans systemClassII_RTime) {
        BatchMeansStatistics.systemClassII_RTime = systemClassII_RTime;
    }

    public static BatchMeans getSystemThroughput() {
        return systemThroughput;
    }

    public static void setSystemThroughput(BatchMeans systemThroughput) {
        BatchMeansStatistics.systemThroughput = systemThroughput;
    }

    public static BatchMeans getSystemThroughput_ClassI() {
        return systemThroughput_ClassI;
    }

    public static void setSystemThroughput_ClassI(BatchMeans systemThroughput_ClassI) {
        BatchMeansStatistics.systemThroughput_ClassI = systemThroughput_ClassI;
    }

    public static BatchMeans getSystemThroughput_ClassII() {
        return systemThroughput_ClassII;
    }

    public static void setSystemThroughput_ClassII(BatchMeans systemThroughput_ClassII) {
        BatchMeansStatistics.systemThroughput_ClassII = systemThroughput_ClassII;
    }

    public static BatchMeans getCloudletThroughput() {
        return cloudletThroughput;
    }

    public static void setCloudletThroughput(BatchMeans cloudletThroughput) {
        BatchMeansStatistics.cloudletThroughput = cloudletThroughput;
    }

    public static BatchMeans getCloudletThroughput_ClassI() {
        return cloudletThroughput_ClassI;
    }

    public static void setCloudletThroughput_ClassI(BatchMeans cloudletThroughput_ClassI) {
        BatchMeansStatistics.cloudletThroughput_ClassI = cloudletThroughput_ClassI;
    }

    public static BatchMeans getCloudletThroughput_ClassII() {
        return cloudletThroughput_ClassII;
    }

    public static void setCloudletThroughput_ClassII(BatchMeans cloudletThroughput_ClassII) {
        BatchMeansStatistics.cloudletThroughput_ClassII = cloudletThroughput_ClassII;
    }

    public static BatchMeans getInterruptedTasks_classIIpercentage() {
        return interruptedTasks_classIIpercentage;
    }

    public static void setInterruptedTasks_classIIpercentage(BatchMeans interruptedTasks_classII) {
        BatchMeansStatistics.interruptedTasks_classIIpercentage = interruptedTasks_classII;
    }

    public static BatchMeans getInterruptedTasks_classII_RTime() {
        return interruptedTasks_classII_RTime;
    }

    public static void setInterruptedTasks_classII_RTime(BatchMeans interruptedTasks_classII_RTime) {
        BatchMeansStatistics.interruptedTasks_classII_RTime = interruptedTasks_classII_RTime;
    }

    public static BatchMeans getCloudletPopulation() {
        return cloudletPopulation;
    }

    public static void setCloudletPopulation(BatchMeans cloudletPopulation) {
        BatchMeansStatistics.cloudletPopulation = cloudletPopulation;
    }

    public static BatchMeans getCloudletClassI_Population() {
        return cloudletClassI_Population;
    }

    public static void setCloudletClassI_Population(BatchMeans cloudletClassI_Population) {
        BatchMeansStatistics.cloudletClassI_Population = cloudletClassI_Population;
    }

    public static BatchMeans getCloudletClassII_Population() {
        return cloudletClassII_Population;
    }

    public static void setCloudletClassII_Population(BatchMeans cloudletClassII_Population) {
        BatchMeansStatistics.cloudletClassII_Population = cloudletClassII_Population;
    }

    public static BatchMeans getCloudPopulation() {
        return cloudPopulation;
    }

    public static void setCloudPopulation(BatchMeans cloudPopulation) {
        BatchMeansStatistics.cloudPopulation = cloudPopulation;
    }

    public static BatchMeans getCloudClassI_Population() {
        return cloudClassI_Population;
    }

    public static void setCloudClassI_Population(BatchMeans cloudClassI_Population) {
        BatchMeansStatistics.cloudClassI_Population = cloudClassI_Population;
    }

    public static BatchMeans getCloudClassII_Population() {
        return cloudClassII_Population;
    }

    public static void setCloudClassII_Population(BatchMeans cloudClassII_Population) {
        BatchMeansStatistics.cloudClassII_Population = cloudClassII_Population;
    }

    public static BatchMeans getCloudletRTime() {
        return cloudletRTime;
    }

    public static void setCloudletRTime(BatchMeans cloudletRTime) {
        BatchMeansStatistics.cloudletRTime = cloudletRTime;
    }

    public static BatchMeans getCloudletClassI_RTime() {
        return cloudletClassI_RTime;
    }

    public static void setCloudletClassI_RTime(BatchMeans cloudletClassI_RTime) {
        BatchMeansStatistics.cloudletClassI_RTime = cloudletClassI_RTime;
    }

    public static BatchMeans getCloudletClassII_RTime() {
        return cloudletClassII_RTime;
    }

    public static void setCloudletClassII_RTime(BatchMeans cloudletClassII_RTime) {
        BatchMeansStatistics.cloudletClassII_RTime = cloudletClassII_RTime;
    }

    public static BatchMeans getCloudRTime() {
        return cloudRTime;
    }

    public static void setCloudRTime(BatchMeans cloudRTime) {
        BatchMeansStatistics.cloudRTime = cloudRTime;
    }

    public static BatchMeans getCloudClassI_RTime() {
        return cloudClassI_RTime;
    }

    public static void setCloudClassI_RTime(BatchMeans cloudClassI_RTime) {
        BatchMeansStatistics.cloudClassI_RTime = cloudClassI_RTime;
    }

    public static BatchMeans getCloudClassII_RTime() {
        return cloudClassII_RTime;
    }

    public static void setCloudClassII_RTime(BatchMeans cloudClassII_RTime) {
        BatchMeansStatistics.cloudClassII_RTime = cloudClassII_RTime;
    }

    public static BatchMeans getCloudThroughput() {
        return cloudThroughput;
    }

    public static void setCloudThroughput(BatchMeans cloudThroughput) {
        BatchMeansStatistics.cloudThroughput = cloudThroughput;
    }

    public static BatchMeans getCloudThroughput_ClassI() {
        return cloudThroughput_ClassI;
    }

    public static void setCloudThroughput_ClassI(BatchMeans cloudThroughput_ClassI) {
        BatchMeansStatistics.cloudThroughput_ClassI = cloudThroughput_ClassI;
    }

    public static BatchMeans getCloudThroughput_ClassII() {
        return cloudThroughput_ClassII;
    }

    public static void setCloudThroughput_ClassII(BatchMeans cloudThroughput_ClassII) {
        BatchMeansStatistics.cloudThroughput_ClassII = cloudThroughput_ClassII;
    }

    public static void setMe(BatchMeansStatistics me) {
        BatchMeansStatistics.me = me;
    }
}
