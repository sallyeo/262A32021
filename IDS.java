/*
262 Assignment 3
Members:
Sally (4603229)
Goh Wee Shen (6747747)
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class IDS
{
    public static void main(String[] args) throws FileNotFoundException
    {
        EventType[] initialStats;

        if(args.length!=3)
        {
            System.out.println("Usage: IDS Events.txt Stats.txt Days");
            System.exit(-1);
        }

        // read from Events.txt
        initialStats=ReadEventFile(args[0]);

        // read from Stats.txt and store to initialStats
        ReadStatsFile(args[1],initialStats);

        // assign number of days
        int baseDays=Integer.parseInt(args[2]);

        // Activity Engine - each base of day
        ActivityEngine baseActivityEngine = new ActivityEngine(initialStats);
        System.out.println("Generating & logging events...");
        baseActivityEngine.generateLogs(baseDays,"day");
        System.out.println("Event Logging Complete...");

        /*
        // Analysis Engine - total of calculated
        AnalysisEngine baseAnalysysEngine = new AnalysisEngine(initialStats);
        System.out.println("Analyzing Events");
        EventType[] baseStats = baseAnalysysEngine.processLogs(baseDays,"base_day");
        System.out.println("Done");

        Scanner sc = new Scanner(System.in);

        while(true)
        {
            System.out.println("Enter StatsFile Days: ");
            String line=sc.nextLine();
            if(line.equals("quit"))
                break;

            System.out.println(line);
            String elements[]=line.split(" ");


            if(elements.length ==2)
            //if(line.equals("TestStats1.txt 10"))
            {

                int newDays = Integer.parseInt(elements[1]);
                EventType[] newStats = EventType.cloneEvents(initialStats);
                ReadStatsFile(elements[0],newStats);


                ActivityEngine liveActivityEngine = new ActivityEngine(newStats);
                System.out.println("Generating Events1");
                liveActivityEngine.generateLogs(newDays,"live_day");
                System.out.println("Done");

                AlertEngine alertEngine = new AlertEngine(baseStats);

                alertEngine.testAlerts(newDays, "live_day");

            }
            else
            {
                System.out.println("Expected: StatsFile Days");
            }
        }
        */
    }   // end of main

    // read event types from file
    static EventType[] ReadEventFile(String filename) throws FileNotFoundException
    {
        Scanner file = new Scanner(new FileReader(filename));
        String numLine = file.nextLine();   // skip the first line
        int eventCount = Integer.parseInt(numLine); // count number of events
        EventType[] eventTypes = new EventType[eventCount];

        for(int i = 0; i < eventCount; i++)
        {
            String line = file.nextLine();
            String[] elements = line.split(":");

            String name = elements[0];
            String type = elements[1];
            String minStr = elements[2];
            String maxStr = elements[3];
            String weightStr = elements[4];

            // set to maximum if empty
            double maxDouble = Double.MAX_VALUE;

            // set to zero if empty
            double weightDouble = 0;
            double minDouble = 0;

            // if not empty
            if(!minStr.equals(""))
                minDouble = Double.parseDouble(minStr);

            // if not empty
            if(!maxStr.equals(""))
                maxDouble = Double.parseDouble(maxStr);

            // if not empty
            if(!weightStr.equals(""))
                weightDouble = Double.parseDouble(weightStr);

            // add event to eventTypes array
            eventTypes[i] = new EventType(name, type.charAt(0),
                                            minDouble, maxDouble, weightDouble);
        }
        return eventTypes;
    }   // end of ReadEventFile()

    // read Stats file, update each event mean and std from the Stats file
    static void ReadStatsFile(String filename, EventType[] eventTypes) throws FileNotFoundException
    {
        Scanner file = new Scanner(new FileReader(filename));
        String numLine = file.nextLine();   // skip first line
        int statCount = Integer.parseInt(numLine);  // count number of stats
        for(int i = 0; i < statCount; i++)
        {
            String line = file.nextLine();
            String[] elements = line.split(":");

            String name = elements[0];
            String meanStr = elements[1];
            String stdStr = elements[2];

            double meanDouble = Double.parseDouble(meanStr);
            double stdDouble = Double.parseDouble(stdStr);

            // update mean and std value of the matching event type
            for(int j = 0; j < eventTypes.length; j++)
            {
                if (eventTypes[j].getName().equals(name))
                {
                    eventTypes[j].setMean(meanDouble);
                    eventTypes[j].setStd(stdDouble);
                }
            }   // end of for loop
        }   // end of for loop

        // for debugging
        for(int j = 0; j < eventTypes.length; j++)
        {
            System.out.printf("%-12s: %s%n", "Event Name", eventTypes[j].getName());
            System.out.printf("%-12s: %s%n", "Event Type", eventTypes[j].getType());
            System.out.printf("%-12s: %s%n", "Event Min", eventTypes[j].getMin());
            System.out.printf("%-12s: %s%n", "Event Max", eventTypes[j].getMax());
            System.out.printf("%-12s: %s%n", "Event Weight", eventTypes[j].getWeight());
            System.out.printf("%-12s: %s%n", "Event Mean", eventTypes[j].getMean());
            System.out.printf("%-12s: %s%n%n", "Event Std", eventTypes[j].getStd());
        }
    }   // end of ReadStatsFile()
}   // end of IDS class

