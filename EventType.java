/*
262 Assignment 3
Members:
Sally (4603229)
Goh Wee Shen (6747747)
*/


import java.util.Random;


class EventType
{
    String name;
    char type;
    double min, max, weight, mean, std;

    public static EventType[] cloneEvents(EventType[] arg)
    {
        EventType newEvents[] = new EventType[arg.length];

        for(int i = 0; i < arg.length; i++)
            newEvents[i] = new EventType(arg[i]);

        return newEvents;
    }

    // constructor
    public EventType(String name, char type,double min, double max, double weight)
    {
        this.name=name;
        this.type=type;
        this.min=min;
        this.max=max;
        this.weight=weight;

        // set some default values in case we don't update this
        this.mean=(min+max)/2;
        this.std=(min-max)/2;
    }   // end of constructor

    // copy constructor
    public EventType(EventType copy)
    {
        this.name=copy.name;
        this.type=copy.type;
        this.min=copy.min;
        this.max=copy.max;
        this.weight=copy.weight;

        this.mean=copy.mean;
        this.std=copy.std;
    }   // end of copy constructor

    // accessor methods
    public String getName()
    {
        return name;
    }

    public char getType()
    {
        return type;
    }

    public double getMin()
    {
        return min;
    }

    public double getMax()
    {
        return max;
    }

    public double getWeight()
    {
        return weight;
    }

    public double getMean()
    {
        return mean;
    }

    public double getStd()
    {
        return std;
    }

    // mutator method
    public void setMean(double mean)
    {
        this.mean = mean;
    }

    public void setStd(double std)
    {
        this.std = std;
    }

    public double getRandValue()
    {
        Random rand = new Random();
        // next Gaussian has mean 0 and std dev 1, so we scale and add
        while(true)
        {
            double value = mean + std*rand.nextGaussian();

            // check for event type first then check within min and max
            if(type == 'D')
            {
                // if discrete round to whole
                value = Math.round(value);
            }

            if(value >= min && value <= max)
            {
                // if within min and max
                return value;
            }
        }   // end of while loop
    }   // end of getRandValue()

};