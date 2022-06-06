package examples;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * https://developer.here.com/documentation/geocoder/topics/quick-start.html?cid=email-hereautomation-evaluation2-Rest-API-Quickstart-05
 *
 * https://developer.here.com/documentation/geocoding-search-api/dev_guide/topics/endpoint-geocode-brief.html
 *
 * @author tm352 module team
 */
public class HereGeocode
{
    //You need to get your own API key at developer.here.com
    static final String API_KEY = "auMFOuPKkz3eEyK3HFsQSLx3mkDqi2Miyanast0RnaA";

    public static void main(String[] args) throws IOException
    {
        //HereGeocode.Example("Saltdean");
        //HereGeocode.Example("St+Giles+Square+Northampton");
        HereGeocode.Example("CV376BB");
    }

    private static void Example(String place)
    {
        final String ROOT = "https://geocode.search.hereapi.com/v1/geocode";
        //The key is appended to the root URL as a parameter, along with the place
        //we want to look for on maps (parameter q)       
        String urlString = String.format("%s?q=%s&apiKey=%s",
                                          ROOT, place, API_KEY);

        JsonObject obj = getJson(urlString);

        //Proof we were able to access the API
        //System.out.println("We got " + obj);

        //What did we get back? Look at each value-name pair
        //Basics.tellMeAbout(obj);

        if (obj != null && !obj.isEmpty())
        {
            //Retrieve the items array from the response 
            JsonArray items = obj.getJsonArray("items");
            
            //see what's in the array
            Basics.tellMeAbout(items);

            if (items.size() > 0)
            {
                //Print out a little information about all the places found in the result array
                System.out.printf("%n%s could refer to%n", place);
                for (int i = 0; i < items.size(); i++)
                {
                    JsonObject ob = items.getJsonObject(i);
                    String title = ob.getJsonString("title").getString();   
                    String postalCode = ob.getJsonObject("address").getJsonString("postalCode").getString();
                    System.out.println("  " + title + " " + postalCode);
                }
            }
        }
        else
        {            
            System.out.println("Sorry, nothing to process");
        }
    }

    /**
     * Utility to retrieve JSON from the provided URL
     *
     * @param urlString Retrieve JSON from here
     * @return a JsonObject
     */
    static JsonObject getJson(String urlString)
    {
        JsonObject res = Basics.emptyObject();

        URL url;

        try
        {
            url = new URL(urlString);
        }
        catch (MalformedURLException ex)
        {
            System.out.println("Sorry, the URL " + urlString + " was bad.");

            return res;
        }

        //This is an auto-closing try-catch
        try (InputStream is = url.openStream();
                JsonReader rdr = Json.createReader(is))
        {
            //Read the object from the reader
            JsonObject obj = rdr.readObject();

            return obj;
        }
        catch (IOException ex)
        {
            System.out.println("Sorry, that didn't work \n" + ex);

            return res;
        }
    }
}
