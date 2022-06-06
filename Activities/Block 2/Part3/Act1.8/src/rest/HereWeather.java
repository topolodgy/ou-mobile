package rest;

import java.io.IOException;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Other example uses can be seen here 
 * https://developer.here.com/documentation/examples/rest/auto_weather
 * and there is more detail on the API use here
 * https://developer.here.com/documentation/destination-weather/dev_guide/topics/resource-report.html
 *
 * @author tm352 module team
 */
public class HereWeather
{
    // The current Weather API uses a single API key.
    final static String API_KEY = "__CqNdFKRn-a8ZweZZwUJJDi4jg0jt9qPq0XlKmJsRw";    

    public static void main(String[] args)
    {
        Example("Saltdean"); //could be ambiguous
    }

    /**
     * Process a single call to the Weather REST API
     * @param place the place we want weather for (could match more than one place)
     */
    
    static void Example(String place)
    {
        // There Here weather service using an api key is at this uri   
        //It is also possible to return json using report.json
        String uriBase = "https://weather.ls.hereapi.com/weather/1.0/report.xml?";

        //Arguments need to be added to the base URI.
        //There are various 'products' we can access. Below we used 'observation'
        //'name' specifies the location we want weather for, but you can be more 
        //specific using postcode, e.g. or latitude and longitude
        String uriString = String.format("%s&product=%s&name=%s&apiKey=%s",
                uriBase, "observation", place, API_KEY);

        System.out.println("Getting URI " + uriString);

        //process this url
        parseXML(place, uriString);
    }

    /**
     * get the XML, then parse it into something readable using XPath expressions
     * @param place the place we want weather for (may be ambiguous)
     * @param uriString the REST URL we will query for the weather
     */
    static void parseXML(String place, String uriString)
    {
        //We need a DocumentBuilderFactory to construct an xml Document we can parse
        //The XML content we receive from the Here API has a namespace associated with it.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true); //The parser will support namespaces in the XML

        //Create an XPath object to process the XML document
        XPath xpath = XPathFactory.newInstance().newXPath();

        //Set the namespace context for the XML we receive.        
        xpath.setNamespaceContext(new WeatherNamespace());
        
        //XPath expressions to retrieve parts of the result. We found these
        //by examining example XML in Notepad++, using an XML plugin
        final String description = "/weather:WeatherReport/observations/location/observation/description";
        final String lowTemp = "/weather:WeatherReport/observations/location/observation/lowTemperature";
        final String highTemp = "/weather:WeatherReport/observations/location/observation/highTemperature";

        try
        {
            Document xmlDocument = dbf.newDocumentBuilder().parse(uriString);

            String low = (String) xpath.evaluate(
                    lowTemp,
                    xmlDocument, XPathConstants.STRING);

            String high = (String) xpath.evaluate(
                    highTemp,
                    xmlDocument, XPathConstants.STRING);

            String descrip = (String) xpath.evaluate(
                    description,
                    xmlDocument, XPathConstants.STRING);

            //We just took the first result - there could be others
            System.out.printf("The weather in %s from Here API:%n", place);
            System.out.println(descrip);
            System.out.printf("Low temperature: %s C%n", low);
            System.out.printf("High temperature: %s C%n", high);
        }
        catch (ParserConfigurationException | SAXException | IOException ex)
        {
            System.out.println("\n" + ex);
            if (ex.toString().contains("Your%20API"))
            {
                System.out.println("You need to set API_KEY to your personal key!");
            }
        }
        catch (XPathExpressionException ex)
        {
            System.out.println("Error parsing XML " + ex);
        }
    }

    /**
     * This class allows us to set the namespace context for the XML we are
     * parsing. This is based on observation of the raw XML, which says that all
     * of our elements are prefixed by the name 'weather' and its xmlns:
     * <weather:WeatherReport xmlns:weather="http://www.navteq.com/APND/WeatherService/v2.0">
     */
    static class WeatherNamespace implements NamespaceContext
    {
        @Override
        public String getNamespaceURI(String prefix)
        {
            if ("weather".equals(prefix))
            {
                return "http://www.navteq.com/APND/WeatherService/v2.0";
            }
            return null;
        }

        @Override
        public String getPrefix(String namespaceURI)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator getPrefixes(String namespaceURI)
        {
            throw new UnsupportedOperationException();
        }
    }
}