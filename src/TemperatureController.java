
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TemperatureController {


    /* Request parameters */

    private final String BASE_URL;
    private final String ACCESS_TOKEN;
    private final String DEVICE_ID;
    private final String MODULE_ID;
    private final String SCALE;
    private final String TYPE;
    private final String TARGET_URL;


    /* Constructor */

    public TemperatureController (String base_url, String access_token, String device_id, String module_id, String scale, String type) {
        this.BASE_URL = base_url;
        this.ACCESS_TOKEN = access_token;
        this.DEVICE_ID = device_id;
        this.MODULE_ID = module_id;
        this.SCALE = scale;
        this.TYPE = type;

        this.TARGET_URL = BASE_URL + "?" +
                "access_token=" + ACCESS_TOKEN +
                "&device_id=" + DEVICE_ID +
                "&module_id=" + MODULE_ID +
                "&scale=" + SCALE +
                "&type=" + TYPE;
    }

    /* *************************** METHODS ***************************** */


    /** This methods enables to fetch the temperature data from a Weather station
     *
     *  Executes an HTTP request of type GET and returns the response
     *  Uses request params to specify information about the data to retrieve
     *
     * @return If request successful returns an Object, else
     * @throws Exception
     */
    public Object getAllData () throws Exception {

        URL url = new URL(TARGET_URL);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println ("\nRequest URL: " + TARGET_URL);
        System.out.println ("Request Method: GET");
        System.out.println ("Status Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;
    }
}
