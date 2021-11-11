/*
262 Assignment 3
Members:
Sally (4603229)
Goh Wee Shen (6747747)
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;


public class ActivityEngine
{
    EventType[] eventStats;

    // constructor
    public ActivityEngine(EventType[] eventStats)
    {
        this.eventStats = eventStats;
    }

    // time stamp for writing in file,
    String generateTimestamp()
    {
        int h = (int)Math.floor(Math.random()*24);
        int m = (int)Math.floor(Math.random()*60);
        int s = (int)Math.floor(Math.random()*60);

        // "." is used instead of ":" for time format as ":" is already the field separator
        return String.format("%02d.%02d.%02d", h, m, s);
    }

    // generate event logs for days, save each day to each distinct file
    void generateLogs(int days, String filename) throws FileNotFoundException
    {
        for(int i = 0; i < days; i++)
        {
            List<String> eventLog = new ArrayList<String>();
            // generate random values for the day for each event
            for(int j = 0; j < eventStats.length; j++)
            {
                // continuous event
                if(eventStats[j].getType() == 'C')
                {
                    // generate double value for this event
                    double value = eventStats[j].getRandValue();
                    // format to 2 digits
                    eventLog.add(generateTimestamp()+":"+
                                    eventStats[j].getName()+":"+
                                    String.format("%.2f",value));
                }   // end of if
                else
                {
                    // discrete event
                    if (eventStats[j].getType() == 'D')
                    {
                        // generate number of events for this day, round to integer
                        int events = (int)Math.round(eventStats[j].getRandValue());

                        for (int k = 0; k < events; k++)
                        {
                            eventLog.add(generateTimestamp() + ":" +
                                            eventStats[j].getName() + ":" + 1);
                        }   // end of for loop
                    }   // end of if
                }   // end of else
            }   // end of for loop

            // sort by time
            Collections.sort(eventLog);

            // save each string to file
            PrintWriter writer = new PrintWriter(new File(filename+i+".txt"));
            for(String line  : eventLog)
            {
                writer.println(line);
            }
            writer.close();
        }   // end of for loop
    }   // end of generateLogs()
}   // end of ActivityEngine class
