import java.util.ArrayList;
import java.util.Collections;

public class DataProcessing {

    /* raw data set which can be used by class methods */
    private ArrayList<Double> raw_data;

    /* Average value of data set */
    private double average;

    /* Maximum value within data set */
    private double max;

    /* Minimum value within data set */
    private double min;


    /* Constructor */
    public DataProcessing(ArrayList<Double> data) {
        this.raw_data = data;
        this.average = calculateAverage ();
        this.max = Collections.<Double>max (raw_data);
        this.min =  Collections.<Double>min (raw_data);
    }

    /* *************************** METHODS ***************************** */

    private double calculateAverage () {
        int averageValue = 0;
        for (Double value : raw_data) {
            averageValue += value;
        }
        averageValue /= raw_data.size ();
        return averageValue;
    }

    public double getAverage() {
        return average;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

}
