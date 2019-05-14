import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main {

    /* Default Request parameter */

    private final String BASE_URL = "https://api.netatmo.com/api/getmeasure";
    private final String ACCESS_TOKEN = "";
    private final String DEVICE_ID = "70:ee:50:00:02:0a";
    private final String MODULE_ID = "02:00:00:01:e6:d4";
    private final String SCALE = "max";
    private final String TYPE = "temperature";

    /* ArrayList containing all temperature values */

    private ArrayList<Double> temperatures = new ArrayList<> ();



    /* *************************** METHODS ***************************** */

    public static void main (String[] args) {
        Main program = new Main();
        program.run();
    }

    private void run () {
        loadTemperatures ();
        DataProcessing data = new DataProcessing (this.temperatures);
        System.out.println ("\n----------- Results ----------\n");
        System.out.println ("average: " + data.getAverage ());
        System.out.println ("min: " + data.getMin ());
        System.out.println ("max: " + data.getMax ());
    }


    /** This method fetch temperatures data from Weather station,
     *  and save them as a global variable within this class:
     *
     *  * Specifies request params
     *  * makes HTTP request
     *  * extract values from request response
     *
     */
    private void loadTemperatures () {

        try {
            TemperatureController tempController = new TemperatureController (
                    BASE_URL, ACCESS_TOKEN, DEVICE_ID, MODULE_ID, SCALE, TYPE);
            Object response = tempController.getAllData ();
            extractTemperatures (response);

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


    /** This method given the response Object of an Http request,
     *
     *  * extract the array of JSON objects
     *  * extract the value we are interested in (Temperature) from each JSON object
     *  * each value has a type: double
     *  * save each value in an ArrayList
     *
     * @param response
     */
    private void extractTemperatures (Object response) {
        if (response == null) throw new IllegalArgumentException ("Response parameter cannot be null");

        // Removes '{body:' at the start and '}' at end of response
        String raw_data = response.toString ().substring (8, response.toString ().length ()-1);
        JSONArray data = new JSONArray (raw_data);

        // Extract each JSON object temperature value
        for (int i=0; i<data.length (); i++) {

            JSONObject json = new JSONObject ("{record:" + data.get (i)+"}");
            Object values = json.getJSONObject ("record").get ("value");
            String strValues = values.toString ()
                    .replaceAll ("\\[", "")
                    .replaceAll ("\\]", "");

            String[] arrValues = strValues.split (",");

            for (String val : arrValues) {
                temperatures.add (Double.parseDouble (val));
            }
        }
    }
}
